package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.ReplyMarkup
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.parser._
import io.circe.{Encoder, Json}
import requests.Requester

import scala.util.{Failure, Success, Try}

case class SendMessage(
    token: BotToken,
    chatId: Either[String, Int],
    text: String,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disablePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
) extends TelegramRequest[TelegramMessage] {

  val request: Requester = requests.post

  val endpoint: String = "sendMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
    "text" -> text,
    "parse_mode" -> parseMode,
    "entities" -> entities,
    "disable_web_page_preview" -> disablePreview,
    "disable_notification" -> disableNotification,
    "reply_to_message_id" -> replyToMessageId,
    "allow_sending_without_reply" -> allowSendingWithoutReply,
    "reply_markup" -> (replyMarkup match {
      case Some(markup) =>
        Encoder[ReplyMarkup].snakeCase(markup).toString.filter(_ >= ' ')
      case None => None
    })
  )

  def parseSuccessResponse(json: Json): Try[TelegramMessage] =
    decode[TelegramMessage](json.toString()) match {
      case Right(message) => Success(message)
      case Left(error)    => Failure(error)
    }
}
