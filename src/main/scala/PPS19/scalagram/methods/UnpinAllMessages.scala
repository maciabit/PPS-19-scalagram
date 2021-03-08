package PPS19.scalagram.methods

import PPS19.scalagram.models.{BotToken, ChatId}
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

/** Use this method to clear the list of pinned messages in a chat.
  * If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have
  * the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
  * Returns true on success, false otherwise.
  *
  * @param token  Token of the bot that will perform the requets.
  * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  */
case class UnpinAllMessages(token: BotToken, chatId: ChatId) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "unpinAllChatMessages"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get
  )

  def parseSuccessfulResponse(json: Json): Try[Boolean] = Success(true)
}
