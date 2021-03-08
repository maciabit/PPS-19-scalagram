package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.models.ChatId
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

/** Use this method to remove a message from the list of pinned messages in a chat.
  * If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have
  * the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
  * Returns true on success, false otherwise.
  *
  * @param token     Token of the bot that will perform the requets.
  * @param chatId    Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  * @param messageId Identifier of a message to unpin.
  */
case class UnpinMessage(
    token: BotToken,
    chatId: ChatId,
    messageId: Int
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "unpinChatMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "message_id" -> messageId
  )

  def parseSuccessfulResponse(json: Json): Try[Boolean] = Success(true)
}
