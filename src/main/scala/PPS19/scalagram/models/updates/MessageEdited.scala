package PPS19.scalagram.models.updates

import PPS19.scalagram.models.payloads.TelegramMessage

/** Represents an update of a message edited in a chat.
  *
  * @param updateId      Unique identifier for this update.
  * @param editedMessage The edited message.
  *
  *                      Extends [[MessageUpdate]]
  */
final case class MessageEdited(
    updateId: Long,
    editedMessage: TelegramMessage
) extends MessageUpdate {

  /** Returns the edited message of the update. Used to retrieve the update message always with .message independently form the update type. */
  override def message: TelegramMessage = editedMessage
}
