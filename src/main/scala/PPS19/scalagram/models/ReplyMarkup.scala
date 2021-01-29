package PPS19.scalagram.models

import io.circe.Encoder
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

case class InlineKeyboardMarkup (prova: String) extends ReplyMarkup
case class ReplyKeyboardMarkup (keyboard: Seq[Seq[KeyboardButton]], resize_keyboard:Option[Boolean] = None, one_time_keyboard:Option[Boolean] = None, selective:Option[Boolean] = None) extends ReplyMarkup
case class ReplyKeyboardRemove (remove_keyboard:Boolean = true, selective:Option[Boolean] = None) extends ReplyMarkup
case class ForceReply (force_reply : Boolean = true, selective : Option[Boolean] = None) extends ReplyMarkup
