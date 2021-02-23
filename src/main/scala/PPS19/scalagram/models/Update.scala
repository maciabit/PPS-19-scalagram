package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import PPS19.scalagram.models.UpdateType.UpdateType
import PPS19.scalagram.models.messages._
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

sealed trait Update {
  val updateId: Long
  //noinspection MutatorLikeMethodIsParameterless
  def updateType: UpdateType
}

object Update {
  implicit val updateDecoder: Decoder[Update] = List[Decoder[Update]](
    deriveDecoder[MessageReceived].widen,
    deriveDecoder[MessageEdited].widen,
    deriveDecoder[ChannelPost].widen,
    deriveDecoder[ChannelPostEdited].widen,
    deriveDecoder[CallbackButtonSelected].widen,
    deriveDecoder[UnknownUpdate].widen
  ).reduceLeft(_.or(_)).camelCase
}

trait ChatUpdate extends Update {
  def chat: Chat
}

abstract class MessageUpdate extends ChatUpdate {

  def message: TelegramMessage

  def chat: Chat = message.chat

  def from: Option[User] =
    message match {
      case message: UserMessage => message.from
      case _                    => None
    }

  override def updateType: UpdateType = {
    message match {
      case _: UserMessage =>
        this match {
          case _: MessageReceived   => UpdateType.MessageReceived
          case _: ChannelPost       => UpdateType.ChannelPostReceived
          case _: MessageEdited     => UpdateType.MessageEdited
          case _: ChannelPostEdited => UpdateType.ChannelPostEdited
        }
      case _: MessagePinned     => UpdateType.MessagePinned
      case _: ChatMembersAdded  => UpdateType.ChatMembersAdded
      case _: ChatMemberRemoved => UpdateType.ChatMemberRemoved
    }
  }
}

object MessageUpdate {
  def unapply(update: MessageUpdate): Option[(Long, TelegramMessage)] =
    Some(update.updateId, update.message)
}

final case class MessageReceived(
    updateId: Long,
    message: TelegramMessage
) extends MessageUpdate

final case class MessageEdited(
    updateId: Long,
    editedMessage: TelegramMessage
) extends MessageUpdate {
  override def message: TelegramMessage = editedMessage
}

final case class ChannelPost(
    updateId: Long,
    channelPost: TelegramMessage
) extends MessageUpdate {
  override def message: TelegramMessage = channelPost
}

final case class ChannelPostEdited(
    updateId: Long,
    editedChannelPost: TelegramMessage
) extends MessageUpdate {
  override def message: TelegramMessage = editedChannelPost
}

final case class CallbackButtonSelected(
    updateId: Long,
    callbackQuery: Callback
) extends ChatUpdate {

  override def updateType: UpdateType = UpdateType.CallbackSelected

  override def chat: Chat =
    callbackQuery.message match {
      case Some(telegramMessage) => telegramMessage.chat
      case _                     => UnknownChat()
    }
}

final case class UnknownUpdate(updateId: Long) extends Update {
  override def updateType: UpdateType = UpdateType.Unknown
}
