package PPS19.scalagram.models.messages

import PPS19.scalagram.marshalling.codecs.DecoderOps
import PPS19.scalagram.models.Chat
import cats.syntax.functor._
import io.circe.Decoder

trait TelegramMessage {
  def messageId: Int
  val chat: Chat
  def date: Int
}

object TelegramMessage {
  implicit val telegramMessageDecoder: Decoder[TelegramMessage] =
    List[Decoder[TelegramMessage]](
      UserMessage.userMessageDecoder.widen,
      SystemMessage.systemMessageDecoder.widen
    ).reduceLeft(_.or(_)).camelCase
}
