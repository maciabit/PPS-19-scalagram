package PPS19.scalagram.methods

import requests.Requester

import scala.util.{Failure, Success, Try}

case class AnswerCallbackQuery(
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

  def call(): Try[Boolean] = perform() match {
    case Success(_) => Success(true)
    case Failure(error) => Failure(error)
  }
}