package PPS19.scalagram.models.messages
import PPS19.scalagram.models._
import cats.syntax.functor._
import io.circe.generic.semiauto.deriveDecoder
import io.circe.Decoder

trait UserMessage extends TelegramMessage {

  /*
  def from: Option[User]

  def forwardFrom: Option[User]

  def forwardFromChat: Option[Chat]





  def replyToMessage: Option[TelegramMessage]





  def viaBot: Option[User]
*/
  def senderChat : Option[Chat]
  def authorSignature: Option[String]
  def editDate: Option[Int]
  def forwardDate: Option[Int]
  def forwardFromMessageId: Option[Int]
  def forwardSignature: Option[String]
  def forwardSenderName: Option[String]
}

object UserMessage {
  implicit val userMessageDecoder: Decoder[UserMessage] =
    List[Decoder[UserMessage]](
      deriveDecoder[TextMessage].widen,
    ).reduceLeft(_.or(_))
}
