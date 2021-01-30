package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import PPS19.scalagram.models.messages._
import cats.syntax.functor._

sealed trait Update {
  def updateId : Long
}

object Update {
  final case class Unknown(updateId: Long) extends Update

  /*implicit val updateDecoder : Decoder[Update] = List[Decoder[Update]](
    deriveDecoder[MessageReceived].widen,
    deriveDecoder[MessageEdited].widen,
    deriveDecoder[ChannelPost].widen,
    deriveDecoder[ChannelPostEdited].widen,
    deriveDecoder[Unknown].widen
  ).reduceLeft(_.or(_)).camelCase*/

  implicit val updateDecoder: Decoder[Update] = List[Decoder[Update]](
    deriveDecoder[MessageReceived].widen,
    deriveDecoder[MessageEdited].widen,
    deriveDecoder[ChannelPost].widen,
    deriveDecoder[ChannelPostEdited].widen,
    deriveDecoder[Unknown].widen
  ).reduceLeft(_.or(_)).camelCase
}

final case class MessageReceived(updateId: Long, message: TelegramMessage) extends Update
final case class MessageEdited(updateId: Long, editedMessage: TelegramMessage) extends Update
final case class ChannelPost(updateId: Long, channelPost: TelegramMessage) extends Update
final case class ChannelPostEdited(updateId: Long, editedChannelPost: TelegramMessage) extends Update