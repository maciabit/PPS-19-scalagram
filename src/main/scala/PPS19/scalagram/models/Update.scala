package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import PPS19.scalagram.models.UpdateType.UpdateType
import PPS19.scalagram.models.messages._
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

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
  def message: TelegramMessage
  def messageType: UpdateType
  def chatId: ChatId
  def from: Option[User] =
    message match {
      case message: UserMessage => message.from
      case _                    => None
    }
}

object MessageUpdate {
  def unapply(update: MessageUpdate): Option[(Long, TelegramMessage)] =
    Some(update.updateId, update.message)
}

final case class MessageReceived(updateId: Long, message: TelegramMessage)
    extends MessageUpdate {
  override def messageType: UpdateType = UpdateType.MessageReceived
  override def chatId: ChatId = ChatId(message.chat.id)
}

final case class MessageEdited(updateId: Long, editedMessage: TelegramMessage)
    extends MessageUpdate {
  override def message: TelegramMessage = editedMessage
  override def messageType: UpdateType = UpdateType.MessageEdited
  override def chatId: ChatId = ChatId(editedMessage.chat.id)
}

final case class ChannelPost(updateId: Long, channelPost: TelegramMessage)
    extends MessageUpdate {
  override def message: TelegramMessage = channelPost
  override def messageType: UpdateType = UpdateType.ChannelPost
  override def chatId: ChatId = ChatId(channelPost.chat.id)
}

final case class ChannelPostEdited(
    updateId: Long,
    editedChannelPost: TelegramMessage
) extends MessageUpdate {
  override def message: TelegramMessage = editedChannelPost
  override def messageType: UpdateType = UpdateType.ChannelPostEdited
  override def chatId: ChatId = ChatId(editedChannelPost.chat.id)
}

final case class CallbackButtonSelected(updateId: Long, callbackQuery: Callback)
    extends Update
