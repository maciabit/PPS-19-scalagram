package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.messages.TelegramMessage
import PPS19.scalagram.models._
import io.circe.parser.decode
import io.circe.{Encoder, Json}
import requests.Requester

import scala.util.{Failure, Success, Try}

case class SendPhoto(
    token: BotToken,
    chatId: ChatId,
    photo: InputFile,
    caption: Option[String] = None,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
) extends TelegramRequest[TelegramMessage] {

  val request: Requester = requests.post

  val endpoint: String = "sendPhoto"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "photo" -> (photo match {
      case ExistingMedia(fileId) => fileId
      case RemoteMedia(url)      => url
      case UploadMedia(_)        => None
    }),
    "caption" -> caption,
    "parse_mode" -> parseMode,
    "entities" -> entities,
    "disable_notification" -> disableNotification,
    "reply_to_message_id" -> replyToMessageId,
    "allow_sending_without_reply" -> allowSendingWithoutReply,
    "reply_markup" -> (replyMarkup match {
      case Some(markup) =>
        Encoder[ReplyMarkup].snakeCase(markup).toString.filter(_ >= ' ')
      case None => None
    })
  )

  override val multipartFormData: Map[String, String] = Map(
    "photo" -> (photo match {
      case UploadMedia(path) => path
      case _                 => null
    })
  ).filter(item => item._2 != null)

  def parseSuccessfulResponse(json: Json): Try[TelegramMessage] =
    decode[TelegramMessage](json.toString()) match {
      case Right(message) => Success(message)
      case Left(error)    => Failure(error)
    }
}
