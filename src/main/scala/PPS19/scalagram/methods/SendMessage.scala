package PPS19.scalagram.methods

import PPS19.scalagram.models.{HttpMethod, ReplyMarkup}
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.Json
import io.circe.parser._
import requests.Response
import io.circe.syntax.EncoderOps

import scala.util.{Failure, Success, Try}

case class SendMessage(){
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "sendMessage")
  def sendMessage(chatId: Either[String, Int],
                  text: String,
                  parseMode: Option[String] = None,
                  entities: Option[Vector[Any]] = None,
                  disablePreview: Option[Boolean] = None,
                  disableNotification: Option[Boolean] = None,
                  replyToMessageId: Option[Int] = None,
                  allowSendingWithoutReply: Option[Boolean] = None,
                  replyMarkup: Option[ReplyMarkup] = None): Try[TelegramMessage] = {
    val urlParams: Map[String, Any] = Map (
      "chat_id" -> chatId.fold(l => l, r => r),
      "text" -> text,
      "parse_mode" -> parseMode,
      "entities" -> entities,
      "disable_web_page_preview" -> disablePreview,
      "disable_notification" -> disableNotification,
      "reply_to_message_id" -> replyToMessageId,
      "allow_sending_without_reply" -> allowSendingWithoutReply,
      "reply_markup" -> replyMarkup.asJson.toString().filter(_ >= ' '),
    ) filter {
      case (_, None) => false
      case _ => true
    } map {
      case (key, Some(value)) => (key, value)
      case (key, value) => (key, value)
    }
    val res = method(urlParams)
    val decoded = decode[TelegramMessage](parse(res.text()).getOrElse(Json.Null).findAllByKey("result").head.toString())
    decoded match {
      case Right(message) => Success(message)
      case Left(error) => Failure(error)
    }
  }
}
