package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class AnswerCallbackQuery(
    token: BotToken,
    callbackQueryId: String,
    text: Option[String],
    showAlert: Option[Boolean],
    url: Option[String],
    cacheTime: Option[Int]
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.get

  val endpoint: String = "answerCallbackQuery"

  val urlParams: Map[String, Any] = Map(
    "callback_query_id" -> callbackQueryId,
    "text" -> text,
    "show_alert" -> showAlert,
    "url" -> url,
    "cache_time" -> cacheTime
  )

  def parseSuccessfulResponse(json: Json): Try[Boolean] = Success(true)
}
