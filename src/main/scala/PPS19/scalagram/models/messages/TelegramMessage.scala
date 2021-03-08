package PPS19.scalagram.models.messages

import PPS19.scalagram.marshalling.codecs.DecoderOps
import PPS19.scalagram.models.Chat
import cats.syntax.functor._
import io.circe.Decoder

/** Defines a generic message sent in Telegram, by a user or by the system.
  *
  * Used by [[PPS19.scalagram.models.messages.UserMessage]], [[PPS19.scalagram.models.messages.SystemMessage]].
  */
trait TelegramMessage {
  def messageId: Int
  val chat: Chat
  def date: Int
}

/** Companion object for TelegramMessage. Used as container for implicit methods. */
object TelegramMessage {
  implicit val telegramMessageDecoder: Decoder[TelegramMessage] =
    List[Decoder[TelegramMessage]](
      UserMessage.userMessageDecoder.widen,
      SystemMessage.systemMessageDecoder.widen
    ).reduceLeft(_.or(_)).camelCase
}
