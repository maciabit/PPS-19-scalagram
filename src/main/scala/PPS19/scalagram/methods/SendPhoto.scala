package PPS19.scalagram.methods

import PPS19.scalagram.models.{ExistingMedia, InputFile, RemoteMedia, UploadMedia}
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.parser.decode
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.ReplyMarkup
import io.circe.Encoder

import scala.util.{Failure, Success, Try}

case class SendPhoto() {

  def call(
    chatId: Either[String, Int],
    photo: InputFile,
    caption: Option[String] = None,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage] = {
    val urlParams: Map[String, Any] = Map (
      "chat_id" -> chatId.fold(l => l, r => r),
      "photo" -> (photo match {
        case ExistingMedia(fileId) => fileId
        case RemoteMedia(url) => url
        case UploadMedia(_) => None
      }),
      "caption" -> caption,
      "parse_mode" -> parseMode,
      "entities" -> entities,
      "disable_notification" -> disableNotification,
      "reply_to_message_id" -> replyToMessageId,
      "allow_sending_without_reply" -> allowSendingWithoutReply,
      "reply_markup" -> (replyMarkup match {
        case Some(markup) => Encoder[ReplyMarkup].snakeCase(markup).toString.filter(_ >= ' ')
        case None => None
      })
    )
    implicit val multipartFormData: Map[String, String] = Map (
      "photo" -> (photo match {
        case UploadMedia(path) => path
        case _ => null
      })
    )
      .filter(item => item._2 != null)
    val res = telegramApiRequest(requests.post, "sendPhoto")(urlParams)
    res match {
      case Success(json) => decode[TelegramMessage](json.toString()) match {
        case Right(message) => Success(message)
        case Left(error) => Failure(error)
      }
      case Failure(error) => Failure(error)
    }
  }
}
