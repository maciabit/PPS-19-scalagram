package PPS19.scalagram.models.updates

import PPS19.scalagram.models.payloads.TelegramMessage

/** Represents an update of a message received in a chat.
  *
  * @param updateId Unique identifier for this update.
  * @param message  New incoming message.
  *
  *                 Extends [[MessageUpdate]]
  */
final case class MessageReceived(
    updateId: Long,
    message: TelegramMessage
) extends MessageUpdate
