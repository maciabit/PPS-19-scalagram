package PPS19.scalagram.models.updates

import PPS19.scalagram.models.payloads._
import PPS19.scalagram.models.updates.UpdateType.UpdateType
import PPS19.scalagram.models.{Chat, User}

/** Represent an update containing a message visible to a user.
  *
  * Used by [[MessageReceived]], [[MessageEdited]], [[ChannelPostReceived]] and [[ChannelPostEdited]].
  *
  * Extends [[ChatUpdate]].
  */
abstract class MessageUpdate extends ChatUpdate {

  /** New incoming message of any kind */
  def message: TelegramMessage

  /** The chat the message belongs to. */
  def chat: Chat = message.chat

  /** (Optional) The user that sent the message. */
  def from: Option[User] =
    message match {
      case message: UserMessage => message.from
      case _                    => None
    }

  /** The type of the update. Needed for practical reason.
    * For system messages the update type can be directly inferred from the message in the update, for user message need to check a nested object.
    */
  override def updateType: UpdateType = {
    message match {
      case _: UserMessage =>
        this match {
          case _: MessageReceived     => UpdateType.MessageReceived
          case _: ChannelPostReceived => UpdateType.ChannelPostReceived
          case _: MessageEdited       => UpdateType.MessageEdited
          case _: ChannelPostEdited   => UpdateType.ChannelPostEdited
        }
      case _: MessagePinned     => UpdateType.MessagePinned
      case _: ChatMembersAdded  => UpdateType.ChatMembersAdded
      case _: ChatMemberRemoved => UpdateType.ChatMemberRemoved
    }
  }
}

/** Companion object for MessageUpdate. Used to redefine unapply method. */
object MessageUpdate {

  /** Destructures a MessageUpdate returning its id and its message. */
  def unapply(update: MessageUpdate): Option[(Long, TelegramMessage)] =
    Some(update.updateId, update.message)
}
