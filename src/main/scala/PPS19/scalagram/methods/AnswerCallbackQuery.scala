package PPS19.scalagram.methods

import PPS19.scalagram.models.BotToken
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

/** Use this method to send answers to callback queries sent from inline keyboards.
  * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
  *
  * @param token           Token of the bot that will perform the requets.
  * @param callbackQueryId Unique identifier for the query to be answered.
  * @param text            Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters.
  * @param showAlert       If true, an alert will be shown by the client instead of a notification at the top of the chat screen.
  *                        Defaults to false.
  * @param url             URL that will be opened by the user's client.
  * @param cacheTime       The maximum amount of time in seconds that the result of the callback query may be cached client-side.
  *                        Defaults to 0.
  */
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
