package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.models.{InlineKeyboardButton, ReplyKeyboardButton}

case class KeyboardButtonContainer(
    text: String,
    url: Option[String] = None,
    callbackData: Option[String] = None,
    switchInlineQuery: Option[String] = None,
    switchInlineQueryCurrentChat: Option[String] = None,
    pay: Option[Boolean] = None,
    requestContact: Option[Boolean] = None,
    requestLocation: Option[Boolean] = None
) {

  def toReplyKeyboardButton: ReplyKeyboardButton =
    ReplyKeyboardButton(text, requestContact, requestLocation)

  def toInlineKeyboardButton: InlineKeyboardButton =
    InlineKeyboardButton(text, url, callbackData, switchInlineQuery, switchInlineQueryCurrentChat, pay)
}
