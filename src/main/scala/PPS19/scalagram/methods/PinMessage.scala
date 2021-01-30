package PPS19.scalagram.methods

import PPS19.scalagram.models.{HttpMethod, TelegramError}
import io.circe.Json
import io.circe.parser.{decode, parse}
import requests.Response

import scala.util.{Failure, Success, Try}

case class PinMessage() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "pinChatMessage")
  def pinMessage(chatId: Either[String,Int], messageId: Int, disableNotification: Option[Boolean]): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
      "disable_notification" -> disableNotification
    ) filter {
      case (_, None) => false
      case _ => true
    } map {
      case (key, Some(value)) => (key, value)
        case(key,value) => (key,value)
    }
    val res = method(urlParams)
    val parsed = parse(res.text()).getOrElse(Json.Null)
    parsed.findAllByKey("ok").head.toString() match {
      case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
      case "true" => Success(true)
    }
  }
}
