package PPS19.scalagram.methods

import PPS19.scalagram.models.{BotToken, ChatId}
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

/** Use this method to add a message to the list of pinned messages in a chat.
  * If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have
  * the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
  * Returns true on success, false otherwise.
  *
  * @param token               Token of the bot that will perform the requets.
  * @param chatId              Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  * @param messageId           Identifier of a message to pin.
  * @param disableNotification Pass true, if it is not necessary to send a notification to all chat members about the new pinned message.
  *                            Notifications are always disabled in channels and private chats.
  */
case class PinMessage(
    token: BotToken,
    chatId: ChatId,
    messageId: Int,
    disableNotification: Option[Boolean]
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "pinChatMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "message_id" -> messageId,
    "disable_notification" -> disableNotification
  )

  def parseSuccessfulResponse(json: Json): Try[Boolean] = Success(true)
}
