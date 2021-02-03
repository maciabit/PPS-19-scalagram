package PPS19.scalagram.methods

import PPS19.scalagram.models.TelegramError
import io.circe.Json
import requests.Response
import io.circe.parser.{decode, parse}

import scala.util.{Failure, Success, Try}

case class AnswerCallbackQuery() {
  val method: Map[String, Any] => Try[Response] = TelegramRequest.telegramApiRequest(requests.get, "answerCallbackQuery")
  def answerCallbackQuery(callbackQueryId: String, text: Option[String], showAlert: Option[Boolean], url:Option[String], cacheTime: Option[Int]): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "callback_query_id" -> callbackQueryId,
      "text" -> text,
      "show_alert" -> showAlert,
      "url" -> url,
      "cache_time" -> cacheTime
    ).filter {
      case (_, None) => false
      case _ => true
    }.map {
      case (key, Some(value)) => (key, value)
      case(key,value) => (key,value)
    }
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
