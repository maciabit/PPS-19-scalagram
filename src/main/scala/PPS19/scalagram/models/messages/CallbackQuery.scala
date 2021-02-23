package PPS19.scalagram.models.messages

import PPS19.scalagram.models.User
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

sealed trait Callback {
  def id: String
  def from: User
  def message: Option[TelegramMessage]
  def inlineMessageId: Option[String]
  def chatInstance: String
  def data: Option[String]
  def gameShortName: Option[String]
}

object Callback {
  implicit val callbackQueryDecoder: Decoder[Callback] =
    List[Decoder[Callback]](
      deriveDecoder[CallbackQuery].widen
    ).reduceLeft(_.or(_))
}

final case class CallbackQuery(
    id: String,
    from: User,
    message: Option[TelegramMessage] = None,
    inlineMessageId: Option[String] = None,
    chatInstance: String,
    data: Option[String] = None,
    gameShortName: Option[String] = None
) extends Callback
