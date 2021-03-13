package PPS19.scalagram.models.updates

import PPS19.scalagram.models.payloads.Callback
import PPS19.scalagram.models.updates.UpdateType.UpdateType
import PPS19.scalagram.models.{Chat, UnknownChat}

/** Represents an update of a callback received in a chat.
  *
  * @param updateId      Unique identifier for this update.
  * @param callbackQuery New incoming callback.
  *
  *                      Extends [[ChatUpdate]]
  */
final case class CallbackButtonSelected(
    updateId: Long,
    callbackQuery: Callback
) extends ChatUpdate {

  /** Return the update type. */
  override def updateType: UpdateType = UpdateType.CallbackSelected

  /** Return the chat the callback was sent to. If the callback does not contain a message, the chat can't be inferred. */
  override def chat: Chat =
    callbackQuery.message match {
      case Some(telegramMessage) => telegramMessage.chat
      case _                     => UnknownChat
    }
}
