package PPS19.scalagram.methods

import PPS19.scalagram.models.{HttpMethod, TelegramError}
import io.circe.Json
import requests.Response
import io.circe.parser.{decode, parse}

import scala.util.{Failure, Success, Try}

case class UnpinMessage() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "unpinChatMessage")
  def unpinMessage(chatId: Either[String,Int], messageId: Int): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
    )
    val res = method(urlParams)
    val parsed = parse(res.text()).getOrElse(Json.Null)
    parsed.findAllByKey("ok").head.toString() match {
      case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
      case "true" => Success(true)
    }
  }
}
