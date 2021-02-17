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

object KeyboardButtonContainer {

  def Callback(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, callbackData = Some(tuple._2))

  def Url(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, url = Some(tuple._2))

  def InlineQuery(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, switchInlineQuery = Some(tuple._2))

  def CurrentChatInlineQuery(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, switchInlineQueryCurrentChat = Some(tuple._2))

  def Payment(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, pay = Some(true))

  def Contact(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, requestContact = Some(true))

  def Location(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, requestLocation = Some(true))
}