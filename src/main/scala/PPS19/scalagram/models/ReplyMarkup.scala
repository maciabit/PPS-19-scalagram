package PPS19.scalagram.models

import io.circe.{Encoder}
import io.circe.syntax._
import io.circe.generic.auto._


sealed trait ReplyMarkup

object ReplyMarkup {
  implicit val encodeReplyMarkup : Encoder[ReplyMarkup] = Encoder.instance {
    case inlineKeyboardMarkup: InlineKeyboardMarkup => inlineKeyboardMarkup.asJson.deepDropNullValues
    case replyKeyboardMarkup: ReplyKeyboardMarkup => replyKeyboardMarkup.asJson.deepDropNullValues
    case replyKeyboardRemove: ReplyKeyboardRemove => replyKeyboardRemove.asJson.deepDropNullValues
    case forceReply: ForceReply => forceReply.asJson.deepDropNullValues
  }
}


case class InlineKeyboardMarkup (inlineKeyboard: Seq[Seq[InlineKeyboardButton]]) extends ReplyMarkup
case class ReplyKeyboardMarkup (keyboard: Seq[Seq[KeyboardButton]], resizeKeyboard:Option[Boolean] = None, oneTimeKeyboard:Option[Boolean] = None, selective:Option[Boolean] = None) extends ReplyMarkup
case class ReplyKeyboardRemove (removeKeyboard:Boolean = true, selective:Option[Boolean] = None) extends ReplyMarkup
case class ForceReply (forceReply : Boolean = true, selective : Option[Boolean] = None) extends ReplyMarkup
