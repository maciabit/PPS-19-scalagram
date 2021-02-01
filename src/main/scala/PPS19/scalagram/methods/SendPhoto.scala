package PPS19.scalagram.methods

import PPS19.scalagram.models.{InputFile, ReplyMarkup, TelegramError}
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.Json
import io.circe.parser.{decode, parse}
import io.circe.syntax.EncoderOps
import requests.Response

import scala.util.{Failure, Success, Try}

case class SendPhoto(){
  val method: Map[String, Any] => Try[Response] = TelegramRequest.telegramApiRequest(requests.post, "sendPhoto")
  def sendPhoto(chatId: Either[String, Int],
                  photo: InputFile,
                  caption: Option[String] = None,
                  parseMode: Option[String] = None,
                  entities: Option[Vector[Any]] = None,
                  disableNotification: Option[Boolean] = None,
                  replyToMessageId: Option[Int] = None,
                  allowSendingWithoutReply: Option[Boolean] = None,
                  replyMarkup: Option[ReplyMarkup] = None): Try[TelegramMessage] = {
    val urlParams: Map[String, Any] = Map (
      "chat_id" -> chatId.fold(l => l, r => r),
      "photo" -> photo.asJson.toString().filter(_ >= ' '),
      "caption" -> caption,
      "parse_mode" -> parseMode,
      "entities" -> entities,
      "disable_notification" -> disableNotification,
      "reply_to_message_id" -> replyToMessageId,
      "allow_sending_without_reply" -> allowSendingWithoutReply,
      "reply_markup" -> replyMarkup.map(_.asJson.toString().filter(_ >= ' '))
    ) filter {
      case (_, None) => false
      case _ => true
    } map {
      case (key, Some(value)) => (key, value)
      case (key, value) => (key, value)
    }
    val res = method(urlParams)
    if(res.isSuccess) {
      val parsed = parse(res.get.text()).getOrElse(Json.Null)
      parsed.findAllByKey("ok").head.toString() match {
        case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
        case "true" =>
          decode[TelegramMessage](parsed.findAllByKey("result").head.toString()) match {
            case Right(message) => Success(message)
            case Left(error) => Failure(error)
          }
      }
    } else {
      Failure(TelegramError.connectionError)
    }
  }
}
