package PPS19.scalagram.methods

import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.Json
import io.circe.parser._
import requests.Response
import scala.util.{Try,Success,Failure}

case class SendMessage(){
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "sendMessage")
  def sendMessage(chatId: Either[String, Int],
                  text: String,
                  parseMode: Option[String],
                  entities: Option[Vector[Any]] = None,
                  disablePreview: Option[Boolean] = None,
                  disableNotification: Option[Boolean] = None,
                  replyToMessageId: Option[Int],
                  allowSendingWithoutReply: Option[Boolean],
                  replyMarkup: Option[Markup] = None): Try[TelegramMessage] = {
    val urlParams: Map[String, Any] = Map (
      "chat_id" -> chatId,
      "text" -> text,
      "parse_mode" -> parseMode,
      "entities" -> entities,
      "disable_web_page_preview" -> disablePreview,
      "disable_notification" -> disableNotification,
      "reply_to_message_id" -> replyToMessageId,
      "allow_sending_without_reply" -> allowSendingWithoutReply,
      "reply_markup" -> replyMarkup,
    ) filter {
      case (_, None) => false
      case _ => true
    } map {
      case (key, Some(value)) => (key, value)
      case (key, value) => (key, value)
    }
    val res = method(urlParams)
    val decoded = decode[TelegramMessage](res.text())
    decoded match {
      case Right(message) => Success(message)
      case Left(error) => Failure(error)
    }
  }
}
