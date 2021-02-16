package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax._

sealed trait ReplyMarkup

object ReplyMarkup {
  implicit val encodeReplyMarkup: Encoder[ReplyMarkup] = Encoder.instance {
    case keyboardMarkup: KeyboardMarkup =>
      KeyboardMarkup.encodeKeyboardMarkup(keyboardMarkup)
    case replyKeyboardRemove: ReplyKeyboardRemove =>
      replyKeyboardRemove.asJson.deepDropNullValues
    case forceReply: ForceReply => forceReply.asJson.deepDropNullValues
  }
}

trait KeyboardMarkup extends ReplyMarkup

object KeyboardMarkup {
  implicit val encodeKeyboardMarkup: Encoder[KeyboardMarkup] = Encoder.instance {
    case inlineKeyboardMarkup: InlineKeyboardMarkup =>
      inlineKeyboardMarkup.asJson.deepDropNullValues
    case replyKeyboardMarkup: ReplyKeyboardMarkup =>
      replyKeyboardMarkup.asJson.deepDropNullValues
  }
}

case class InlineKeyboardMarkup(inlineKeyboard: Seq[Seq[InlineKeyboardButton]])
    extends KeyboardMarkup

case class ReplyKeyboardMarkup(
    keyboard: Seq[Seq[ReplyKeyboardButton]],
    resizeKeyboard: Option[Boolean] = None,
    oneTimeKeyboard: Option[Boolean] = None,
    selective: Option[Boolean] = None
) extends KeyboardMarkup

case class ReplyKeyboardRemove(
    removeKeyboard: Boolean = true,
    selective: Option[Boolean] = None
) extends ReplyMarkup

case class ForceReply(
    forceReply: Boolean = true,
    selective: Option[Boolean] = None
) extends ReplyMarkup
