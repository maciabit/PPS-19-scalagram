package PPS19.scalagram.methods

import PPS19.scalagram.models.{HttpMethod, TelegramError}
import io.circe.Json
import requests.Response
import io.circe.parser.{decode, parse}

import scala.util.{Failure, Success, Try}

case class UnpinAllMessages() {
  val method: Map[String, Any] => Try[Response] = TelegramRequest.telegramApiRequest(HttpMethod.POST, "unpinAllChatMessages")
  def unpinAllMessages(chatId: Either[String,Int]): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
    )
    val res = method(urlParams)
    if(res.isSuccess) {
      val parsed = parse(res.get.text()).getOrElse(Json.Null)
      parsed.findAllByKey("ok").head.toString() match {
        case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
        case "true" => Success(true)
      }
    } else {
      println(TelegramError.connectionError.description)
      Failure(TelegramError.connectionError)
    }
  }
}
