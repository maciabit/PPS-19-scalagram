package PPS19.scalagram.models.messages
import PPS19.scalagram.models._

/** Represents an incoming or outgoing text message.
  *
  * @param messageId            Unique message identifier inside a chat.
  * @param chat                 Conversation the message belongs to.
  * @param date                 Date the message was sent in Unix time.
  * @param text                 The UTF-8 text of the message, 0-4096 characters.
  * @param entities             (Optional) Special entities that appear in the message.
  * @param from                 (Optional) Sender.
  * @param forwardFrom          (Optional) Sender of the original message, for forwarder messages.
  * @param forwardFromChat      (Optional) The original chat the message was sent, for forwarded messages.
  * @param forwardFromMessageId (Optional) Identifier of the original message, for forwarded messages.
  * @param forwardSignature     (Optional) Signature of the original message author, for forwarded messages.
  * @param forwardSenderName    (Optional) Sender's name of the original message, for forwarded messages.
  * @param forwardDate          (Optional) Date the original message was sent in Unix time, for forwarded messages.
  * @param replyToMessage       (Optional) The original message, for replies.
  * @param editDate             (Optional) Date the message was last edited in Unix time.
  * @param authorSignature      (Optional) Signature of the message author.
  * @param viaBot               (Optional) Bot through which the message was sent, for messages sent via bot.
  *
  *                             Extends [[PPS19.scalagram.models.messages.UserMessage]].
  */
final case class TextMessage(
    messageId: Int,
    chat: Chat,
    date: Int,
    text: String,
    entities: Option[List[MessageEntity]] = None,
    from: Option[User] = None,
    forwardFrom: Option[User] = None,
    forwardFromChat: Option[Chat] = None,
    forwardFromMessageId: Option[Int] = None,
    forwardSignature: Option[String] = None,
    forwardSenderName: Option[String] = None,
    forwardDate: Option[Int] = None,
    replyToMessage: Option[TelegramMessage] = None,
    editDate: Option[Int] = None,
    authorSignature: Option[String] = None,
    viaBot: Option[User] = None
) extends UserMessage
