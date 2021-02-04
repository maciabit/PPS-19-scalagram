package PPS19.scalagram.methods

import io.circe.Json

import scala.util.{Failure, Success, Try}

case class AnswerCallbackQuery() {

  private val method: Map[String, Any] => Try[Json] = telegramApiRequest(requests.get, "answerCallbackQuery")

  def answerCallbackQuery(
    callbackQueryId: String,
    text: Option[String],
    showAlert: Option[Boolean],
    url: Option[String],
    cacheTime: Option[Int]
  ): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "callback_query_id" -> callbackQueryId,
      "text" -> text,
      "show_alert" -> showAlert,
      "url" -> url,
      "cache_time" -> cacheTime
    )
    val res = method(urlParams)
    res match {
      case Success(_) => Success(true)
      case Failure(error) => Failure(error)
    }
  }
}