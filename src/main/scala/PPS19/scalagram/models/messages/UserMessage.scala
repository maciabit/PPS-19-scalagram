package PPS19.scalagram.models.messages
import PPS19.scalagram.models._
import cats.syntax.functor._
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Decoder

trait UserMessage extends TelegramMessage {

  def from: Option[User]

  def forwardFrom: Option[User]

  def forwardFromChat: Option[Chat]

  def forwardFromMessageId: Option[Int]

  def forwardSignature: Option[String]

  def forwardSenderName: Option[String]

  def forwardDate: Option[Int]

  def replyToMessage: Option[TelegramMessage]

  def editDate: Option[Int]

  def authorSignature: Option[String]

  def viaBot: Option[User]
}

object UserMessage {
  implicit val userMessageDecoder: Decoder[UserMessage] =
    List[Decoder[UserMessage]](
      deriveDecoder[TextMessage].widen,
      deriveDecoder[PhotoMessage].widen
    ).reduceLeft(_.or(_))
}
