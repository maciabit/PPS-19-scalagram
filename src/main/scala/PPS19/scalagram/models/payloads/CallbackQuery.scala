package PPS19.scalagram.models.payloads

import PPS19.scalagram.models.User
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Defines a generic callback.
  *
  * Used by [[PPS19.scalagram.models.payloads.CallbackQuery]]
  */
sealed trait Callback extends Payload {

  /** Unique identifier for this query. */
  def id: String

  /** Sender. */
  def from: User

  /** Message containing the callback button that originated the query. If it is too old, will not be available. */
  def message: Option[TelegramMessage]

  /** Identifier of the message containing the callback sent via the bot in inline bot. */
  def inlineMessageId: Option[String]

  /** Identifier of the chat to which the message with the callback button was sent. */
  def chatInstance: String

  /** Data associated with the callback button. If it is too old, will not be available */
  def data: Option[String]

  /** Short name of a Game to be returned, serves as the unique identifier */
  def gameShortName: Option[String]
}

/** Companion object for Callback. Used as container for implicit methods. */
object Callback {
  implicit val callbackQueryDecoder: Decoder[Callback] =
    List[Decoder[Callback]](
      deriveDecoder[CallbackQuery].widen
    ).reduceLeft(_.or(_))
}

/** Represents an incoming callback query from a callback button in an inline keyboard.
  *
  * @param id              Unique identifier for this query.
  * @param from            Sender.
  * @param message         Message containing the callback button that originated the query. If it is too old, will not be available.
  * @param inlineMessageId Identifier of the message containing the callback sent via the bot in inline bot.
  * @param chatInstance  Identifier of the chat to which the message with the callback button was sent.
  * @param data          Data associated with the callback button. If it is too old, will not be available.
  * @param gameShortName Short name of a Game to be returned, serves as the unique identifier.
  *
  *                      Extends [[PPS19.scalagram.models.payloads.Callback]].
  */
final case class CallbackQuery(
    id: String,
    from: User,
    message: Option[TelegramMessage] = None,
    inlineMessageId: Option[String] = None,
    chatInstance: String,
    data: Option[String] = None,
    gameShortName: Option[String] = None
) extends Callback
