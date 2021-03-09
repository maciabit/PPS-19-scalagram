package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax._

/** Defines a generic markup sent with a message.
  *
  * Used by [[PPS19.scalagram.models.KeyboardMarkup]], [[PPS19.scalagram.models.ReplyKeyboardRemove]] and [[PPS19.scalagram.models.ForceReply]].
  */
sealed trait ReplyMarkup

/** Companion object for ReplyMarkup. Used as container for implicit methods. */
object ReplyMarkup {
  implicit val encodeReplyMarkup: Encoder[ReplyMarkup] = Encoder.instance {
    case keyboardMarkup: KeyboardMarkup =>
      KeyboardMarkup.encodeKeyboardMarkup(keyboardMarkup)
    case replyKeyboardRemove: ReplyKeyboardRemove =>
      replyKeyboardRemove.asJson.deepDropNullValues
    case forceReply: ForceReply => forceReply.asJson.deepDropNullValues
  }
}

/** Defines a generic keyboard sent with a message.
  *
  * Used by [[PPS19.scalagram.models.InlineKeyboardMarkup]] and [[PPS19.scalagram.models.ReplyKeyboardMarkup]].
  *
  * Extends [[PPS19.scalagram.models.ReplyMarkup]]
  */
trait KeyboardMarkup extends ReplyMarkup

/** Companion object for KeyboardMarkup. Used as container for implicit methods. */
object KeyboardMarkup {
  implicit val encodeKeyboardMarkup: Encoder[KeyboardMarkup] =
    Encoder.instance {
      case inlineKeyboardMarkup: InlineKeyboardMarkup =>
        inlineKeyboardMarkup.asJson.deepDropNullValues
      case replyKeyboardMarkup: ReplyKeyboardMarkup =>
        replyKeyboardMarkup.asJson.deepDropNullValues
    }
}

/** Represents an inline keyboard that appears right next to the message it belongs to.
  *
  * @param inlineKeyboard All the buttons the keyboard is composed by.
  *
  *                       Extends [[PPS19.scalagram.models.KeyboardMarkup]].
  */
case class InlineKeyboardMarkup(inlineKeyboard: Seq[Seq[InlineKeyboardButton]]) extends KeyboardMarkup

/** Represents a custom keyboard with reply option which can be used in combination with the classic input field.
  *
  * @param keyboard        All the buttons the keyboard is composed by.
  * @param resizeKeyboard  (Optional) If set true, the client will be requested to resize the keyboard vertically to better fit the screen.
  * @param oneTimeKeyboard (Optional) If set true, the client will be requested hide the keyboard as soon as it will be used.
  * @param selective       (optional) If set true, the keyboard will be shown only to user mentioned in the text and to the original sender if the bot is replying to a message.
  *
  *                        Extends [[PPS19.scalagram.models.KeyboardMarkup]].
  */
case class ReplyKeyboardMarkup(
    keyboard: Seq[Seq[ReplyKeyboardButton]],
    resizeKeyboard: Option[Boolean] = None,
    oneTimeKeyboard: Option[Boolean] = None,
    selective: Option[Boolean] = None
) extends KeyboardMarkup

/** Upon receiving a message with this markup, Telegram will remove the current custom keyboard and display the default input field.
  *
  * @param removeKeyboard True. Requests Telegram to remove the keyboard.
  * @param selective      (Optional) If set true, the keyboard will be removed only for user mentioned in the text and to the original sender if the bot is replying to a message.
  *
  *                       Extends [[PPS19.scalagram.models.ReplyMarkup]].
  */
case class ReplyKeyboardRemove(
    removeKeyboard: Boolean = true,
    selective: Option[Boolean] = None
) extends ReplyMarkup

/** Upon receiving a message with this markup, Telegram clients will display a reply interface to the user.
  *
  * @param forceReply True. Requests Telegram to show the reply interface.
  * @param selective  (Optional) If set true, the reply interface will be shown only to user mentioned in the text and to the original sender if the bot is replying to a message.
  *
  *                   Extends [[PPS19.scalagram.models.ReplyMarkup]].
  */
case class ForceReply(
    forceReply: Boolean = true,
    selective: Option[Boolean] = None
) extends ReplyMarkup
