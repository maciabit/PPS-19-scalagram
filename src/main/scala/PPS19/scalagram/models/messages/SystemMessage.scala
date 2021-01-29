package PPS19.scalagram.models.messages

import PPS19.scalagram.models.Chat
import cats.implicits.toFunctorOps
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

trait SystemMessage extends TelegramMessage {}

object SystemMessage {
  implicit val systemMessageDecoder : Decoder[SystemMessage] =
    List[Decoder[SystemMessage]](
      deriveDecoder[MessagePinned].widen
    ).reduceLeft(_.or(_))
}

final case class MessagePinned(messageId:Int, chat:Chat, date:Int, pinnedMessage:TelegramMessage) extends SystemMessage
