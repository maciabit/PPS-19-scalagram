package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import PPS19.scalagram.models.messages._
import cats.syntax.functor._

sealed trait Update {
  val updateId: Long
}

object Update {
  final case class Unknown(updateId: Long) extends Update

  implicit val updateDecoder: Decoder[Update] = List[Decoder[Update]](
    deriveDecoder[MessageReceived].widen,
    deriveDecoder[MessageEdited].widen,
    deriveDecoder[ChannelPost].widen,
    deriveDecoder[ChannelPostEdited].widen,
    deriveDecoder[CallbackButtonSelected].widen,
    deriveDecoder[Unknown].widen
  ).reduceLeft(_.or(_)).camelCase
}

trait MessageUpdate extends Update {
  val updateId: Long
  val message: TelegramMessage
}

object MessageUpdate {
  def unapply(update: MessageUpdate): Option[(Long, TelegramMessage)] = Some(update.updateId, update.message)
}

final case class MessageReceived(updateId: Long, message: TelegramMessage) extends MessageUpdate

final case class MessageEdited(updateId: Long, editedMessage: TelegramMessage) extends MessageUpdate {
  val message: TelegramMessage = editedMessage
}
final case class ChannelPost(updateId: Long, channelPost: TelegramMessage) extends  MessageUpdate {
  val message: TelegramMessage = channelPost
}
final case class ChannelPostEdited(updateId: Long, editedChannelPost: TelegramMessage) extends  MessageUpdate {
  val message: TelegramMessage = editedChannelPost
}

final case class CallbackButtonSelected(updateId: Long, callbackQuery: Callback) extends Update