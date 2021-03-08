package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.models.ChatId
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

/** Use this method to delete a message, including service messages.
  *
  * @param token     Token of the bot that will perform the requets.
  * @param chatId    Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  * @param messageId Identifier of the message to delete
  */
case class DeleteMessage(
    token: BotToken,
    chatId: ChatId,
    messageId: Int
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.get

  val endpoint: String = "deleteMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "message_id" -> messageId
  )

  def parseSuccessfulResponse(json: Json): Try[Boolean] = Success(true)
}
