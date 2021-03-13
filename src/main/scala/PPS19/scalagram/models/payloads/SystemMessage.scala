package PPS19.scalagram.models.payloads

import PPS19.scalagram.models.{Chat, User}
import cats.implicits.toFunctorOps
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Defines a generic message sent by Telegram, considered the system.
  *
  * Used by [[PPS19.scalagram.models.payloads.MessagePinned]], [[PPS19.scalagram.models.payloads.ChatMembersAdded]], [[PPS19.scalagram.models.payloads.ChatMemberRemoved]].
  *
  * Extends [[PPS19.scalagram.models.payloads.TelegramMessage]].
  */
trait SystemMessage extends TelegramMessage

/** Companion object for SystemMessage. Used as container for implicit methods. */
object SystemMessage {
  implicit val systemMessageDecoder: Decoder[SystemMessage] =
    List[Decoder[SystemMessage]](
      deriveDecoder[MessagePinned].widen,
      deriveDecoder[ChatMembersAdded].widen,
      deriveDecoder[ChatMemberRemoved].widen
    ).reduceLeft(_.or(_))
}

/** Represents an incoming message containing the message added to the list of pinned messages in a chat.
  *
  * @param messageId     Unique message identifier inside a chat.
  * @param chat          Conversation the message belongs to.
  * @param date          Date the message was sent in Unix time.
  * @param pinnedMessage The message added to the list of pinned messages in a chat.
  *
  *                      Extends [[PPS19.scalagram.models.payloads.SystemMessage]].
  */
final case class MessagePinned(
    messageId: Int,
    chat: Chat,
    date: Int,
    pinnedMessage: TelegramMessage
) extends SystemMessage

/** Represents an incoming message containing all the users added to the group or supergroup.
  *
  * @param messageId      Unique message identifier inside a chat.
  * @param chat           Conversation the message belongs to.
  * @param date           Date the message was sent in Unix time.
  * @param newChatMembers The users added to the group or supergroup.
  *
  *                       Extends [[PPS19.scalagram.models.payloads.SystemMessage]].
  */
final case class ChatMembersAdded(
    messageId: Int,
    chat: Chat,
    date: Int,
    newChatMembers: Seq[User]
) extends SystemMessage

/** Represents an incoming message containing the user removed from the group or supergroup.
  *
  * @param messageId      Unique message identifier inside a chat.
  * @param chat           Conversation the message belongs to.
  * @param date           Date the message was sent in Unix time.
  * @param leftChatMember The user removed from the group or supergroup.
  *
  *                       Extends [[PPS19.scalagram.models.payloads.SystemMessage]].
  */
final case class ChatMemberRemoved(
    messageId: Int,
    chat: Chat,
    date: Int,
    leftChatMember: User
) extends SystemMessage
