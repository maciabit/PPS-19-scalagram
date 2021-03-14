package PPS19.scalagram.models.updates

import PPS19.scalagram.models.payloads.TelegramMessage

/** Represents an update of a message edited in a channel.
  *
  * @param updateId          Unique identifier for this update.
  * @param editedChannelPost The edited channel post.
  *
  *                          Extends [[MessageUpdate]]
  */
final case class ChannelPostEdited(
    updateId: Long,
    editedChannelPost: TelegramMessage
) extends MessageUpdate {

  /** Returns the edited channel post of the update. Used to retrieve the update message always with .message independently form the update type. */
  override def message: TelegramMessage = editedChannelPost
}
