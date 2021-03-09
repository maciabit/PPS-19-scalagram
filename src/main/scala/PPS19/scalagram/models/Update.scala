package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import PPS19.scalagram.models.UpdateType.UpdateType
import PPS19.scalagram.models.messages._
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Represents an incoming update for a bot.
  * Used by [[PPS19.scalagram.models.ChatUpdate]], [[PPS19.scalagram.models.UnknownUpdate]],
  */
sealed trait Update {

  /** The update's unique identifier. Update identifiers starts from a certain positive number and increase sequentially. */
  val updateId: Long
  //noinspection MutatorLikeMethodIsParameterless
  def updateType: UpdateType
}

/** Companion object for Update. Used as container for implicit methods. */
object Update {
  implicit val updateDecoder: Decoder[Update] = List[Decoder[Update]](
    deriveDecoder[MessageReceived].widen,
    deriveDecoder[MessageEdited].widen,
    deriveDecoder[ChannelPost].widen,
    deriveDecoder[ChannelPostEdited].widen,
    deriveDecoder[CallbackButtonSelected].widen,
    deriveDecoder[UnknownUpdate].widen
  ).reduceLeft(_.or(_)).camelCase
}

/** Represents an update belonging to a chat. Used to retrieve the update's chat. */
trait ChatUpdate extends Update {

  /** The chat the update belongs to. */
  def chat: Chat
}

/** Represent an update containing a message visible to a user.
  *
  * Used by [[PPS19.scalagram.models.MessageReceived]], [[PPS19.scalagram.models.MessageEdited]], [[PPS19.scalagram.models.ChannelPost]] and [[PPS19.scalagram.models.ChannelPostEdited]].
  *
  * Extends [[PPS19.scalagram.models.ChatUpdate]].
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
          case _: MessageReceived   => UpdateType.MessageReceived
          case _: ChannelPost       => UpdateType.ChannelPostReceived
          case _: MessageEdited     => UpdateType.MessageEdited
          case _: ChannelPostEdited => UpdateType.ChannelPostEdited
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

/** Represents an update of a message received in a chat.
  *
  * @param updateId Unique identifier for this update.
  * @param message  New incoming message.
  *
  *                 Extends [[PPS19.scalagram.models.MessageUpdate]]
  */
final case class MessageReceived(
    updateId: Long,
    message: TelegramMessage
) extends MessageUpdate

/** Represents an update of a message edited in a chat.
  *
  * @param updateId      Unique identifier for this update.
  * @param editedMessage The edited message.
  *
  *                      Extends [[PPS19.scalagram.models.MessageUpdate]]
  */
final case class MessageEdited(
    updateId: Long,
    editedMessage: TelegramMessage
) extends MessageUpdate {

  /** Returns the edited message of the update. Used to retrieve the update message always with .message independently form the update type. */
  override def message: TelegramMessage = editedMessage
}

/** Represents an update of a message received in a channel.
  *
  * @param updateId    Unique identifier for this update.
  * @param channelPost New incoming channel post.
  *
  *                    Extends [[PPS19.scalagram.models.MessageUpdate]]
  */
final case class ChannelPost(
    updateId: Long,
    channelPost: TelegramMessage
) extends MessageUpdate {

  /** Returns the channel post of the update. Used to retrieve the update message always with .message independently form the update type. */
  override def message: TelegramMessage = channelPost
}

/** Represents an update of a message edited in a channel.
  *
  * @param updateId          Unique identifier for this update.
  * @param editedChannelPost The edited channel post.
  *
  *                          Extends [[PPS19.scalagram.models.MessageUpdate]]
  */
final case class ChannelPostEdited(
    updateId: Long,
    editedChannelPost: TelegramMessage
) extends MessageUpdate {

  /** Returns the edited channel post of the update. Used to retrieve the update message always with .message independently form the update type. */
  override def message: TelegramMessage = editedChannelPost
}

/** Represents an update of a callback received in a chat.
  *
  * @param updateId      Unique identifier for this update.
  * @param callbackQuery New incoming callback.
  *
  *                      Extends [[PPS19.scalagram.models.ChatUpdate]]
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
      case _                     => UnknownChat()
    }
}

/** Represent an unknown update. Useful to avoid unexpected crash.
  *
  * @param updateId Unique identifier for this update.
  *
  *                 Extends [[PPS19.scalagram.models.Update]]
  */
final case class UnknownUpdate(updateId: Long) extends Update {
  override def updateType: UpdateType = UpdateType.Unknown
}
