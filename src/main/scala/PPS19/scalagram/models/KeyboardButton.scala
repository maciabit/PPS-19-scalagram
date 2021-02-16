package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

trait KeyboardButton {
  val text: String
}

case class InlineKeyboardButton (
    text: String,
    url: Option[String] = None,
    callbackData: Option[String] = None,
    switchInlineQuery: Option[String] = None,
    switchInlineQueryCurrentChat: Option[String] = None,
    pay: Option[Boolean] = None
) extends KeyboardButton

object InlineKeyboardButton {
  implicit val inlineKeyboardButtonEncoder: Encoder[InlineKeyboardButton] =
    deriveEncoder

  def url(text: String, url: String): InlineKeyboardButton =
    InlineKeyboardButton(text, url = Some(url))

  def callback(text: String, callbackData: String): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackData = Some(callbackData))

  def switchInlineQuery(text: String, switchInlineQuery: String): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQuery = Some(switchInlineQuery))

  def switchInlineQueryCurrentChat(text: String, switchInlineQueryCurrentChat: String): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQueryCurrentChat = Some(switchInlineQueryCurrentChat))

  def pay(text: String): InlineKeyboardButton =
    InlineKeyboardButton(text, pay = Some(true))
}

case class ReplyKeyboardButton(
    text: String,
    requestContact: Option[Boolean] = None,
    requestLocation: Option[Boolean] = None
) extends KeyboardButton

object ReplyKeyboardButton {
  implicit val keyboardButtonEncoder: Encoder[ReplyKeyboardButton] =
    deriveEncoder
}
