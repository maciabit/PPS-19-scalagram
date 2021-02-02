package PPS19.scalagram.methods

import PPS19.scalagram.models.TelegramError
import io.circe.Json
import io.circe.parser.{decode, parse}
import requests.Response

import scala.util.{Failure, Success, Try}

case class DeleteMessage() {
  val method: Map[String, Any] => Try[Response] = TelegramRequest.telegramApiRequest(requests.get, "deleteMessage")
  def deleteMessage(chatId: Either[String,Int], messageId: Int): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
    )
    val res = method(urlParams)
    res match {
      case Success(response) =>
        val parsed = parse(response.text()).getOrElse(Json.Null)
        parsed.findAllByKey("ok").head.toString() match {
          case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
          case "true" => Success(true)
        }
      case Failure(e) => Failure(e)
    }
  }
}
