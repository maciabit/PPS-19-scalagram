package PPS19.scalagram.models.messages

import PPS19.scalagram.models._
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Defines a generic message sent by a user, human or bot.
  *
  * Used by [[PPS19.scalagram.models.messages.TextMessage]], [[PPS19.scalagram.models.messages.PhotoMessage]].
  *
  * Extends [[PPS19.scalagram.models.messages.TelegramMessage]].
  */
trait UserMessage extends TelegramMessage {

  /** (Optional) Sender. */
  def from: Option[User]

  /** (Optional) Sender of the original message, for forwarder messages. */
  def forwardFrom: Option[User]

  /** (Optional) The original chat the message was sent, for forwarded messages. */
  def forwardFromChat: Option[Chat]

  /** (Optional) Identifier of the original message, for forwarded messages. */
  def forwardFromMessageId: Option[Int]

  /** (Optional) Signature of the original message author, for forwarded messages. */
  def forwardSignature: Option[String]

  /** (Optional) Sender's name of the original message, for forwarded messages. */
  def forwardSenderName: Option[String]

  /** (Optional) Date the original message was sent in Unix time, for forwarded messages. */
  def forwardDate: Option[Int]

  /** (Optional) Date the original message was sent in Unix time, for forwarded messages. */
  def replyToMessage: Option[TelegramMessage]

  /** (Optional) Date the message was last edited in Unix time. */
  def editDate: Option[Int]

  /** (Optional) Signature of the message author. */
  def authorSignature: Option[String]

  /** (Optional) Bot through which the message was sent, for messages sent via bot. */
  def viaBot: Option[User]
}

/** Companion object for UserMessage. Used as container for implicit methods. */
object UserMessage {
  implicit val userMessageDecoder: Decoder[UserMessage] =
    List[Decoder[UserMessage]](
      deriveDecoder[TextMessage].widen,
      deriveDecoder[PhotoMessage].widen
    ).reduceLeft(_.or(_))
}
