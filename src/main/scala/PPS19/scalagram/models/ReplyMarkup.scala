package PPS19.scalagram.models

import cats.syntax.functor._
import io.circe.{Decoder, Encoder}
import io.circe.syntax._
import io.circe.generic.auto._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

sealed trait ReplyMarkup

/*
object ReplyMarkup {
  implicit val encodeReplyMarkup : Encoder[ReplyMarkup] = Encoder.instance {
    case inlineKeyboardMarkup: InlineKeyboardMarkup => inlineKeyboardMarkup.asJson.deepDropNullValues
    case replyKeyboardMarkup: ReplyKeyboardMarkup => replyKeyboardMarkup.asJson.deepDropNullValues
    case replyKeyboardRemove: ReplyKeyboardRemove => replyKeyboardRemove.asJson.deepDropNullValues
    case forceReply: ForceReply => forceReply.asJson.deepDropNullValues
  }
}
*/
case class InlineKeyboardMarkup (inlineKeyboard: Seq[Seq[InlineKeyboardButton]]) extends ReplyMarkup
case class ReplyKeyboardMarkup (keyboard: Seq[Seq[KeyboardButton]], resizeKeyboard:Option[Boolean] = None, oneTimeKeyboard:Option[Boolean] = None, selective:Option[Boolean] = None) extends ReplyMarkup
case class ReplyKeyboardRemove (removeKeyboard:Boolean = true, selective:Option[Boolean] = None) extends ReplyMarkup
case class ForceReply (forceReply : Boolean = true, selective : Option[Boolean] = None) extends ReplyMarkup


object boilerPlate {
  implicit val inlineKeyboardMarkupEncoder: Encoder[InlineKeyboardMarkup] = deriveEncoder[InlineKeyboardMarkup]
  implicit val replyKeyboardMarkupEncoder: Encoder[ReplyKeyboardMarkup] = deriveEncoder[ReplyKeyboardMarkup]
  implicit val replyKeyboardRemoveEncoder: Encoder[ReplyKeyboardRemove] = deriveEncoder[ReplyKeyboardRemove]
  implicit val forceReplyEncoder: Encoder[ForceReply] = deriveEncoder[ForceReply]
}