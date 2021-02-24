package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, ReplyMarkup}
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.parser.decode
import io.circe.{Encoder, Json}
import requests.Requester

import scala.util.{Failure, Success, Try}

case class EditMessage(
    token: BotToken,
    chatId: ChatId,
    text: String,
    inlineMessageId: Option[String] = None,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disablePreview: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
) extends TelegramRequest[TelegramMessage] {

  val request: Requester = requests.post

  val endpoint: String = "editMessageText"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "text" -> text,
    "inlineMessageId" -> parseMode,
    "entities" -> entities,
    "disable_web_page_preview" -> disablePreview,
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
