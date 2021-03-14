package PPS19.scalagram.models.updates

import PPS19.scalagram.models.payloads.TelegramMessage

/** Represents an update of a message received in a channel.
  *
  * @param updateId    Unique identifier for this update.
  * @param channelPost New incoming channel post.
  *
  *                    Extends [[MessageUpdate]]
  */
final case class ChannelPostReceived(
    updateId: Long,
    channelPost: TelegramMessage
) extends MessageUpdate {

  /** Returns the channel post of the update. Used to retrieve the update message always with .message independently form the update type. */
  override def message: TelegramMessage = channelPost
}
