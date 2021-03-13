package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.models.{InlineKeyboardButton, ReplyKeyboardButton}

/** Container used to build a [[PPS19.scalagram.models.KeyboardButton]].
  *
  * @param text                         Text of the button.
  * @param url                          (Optional) The url to be opened when the button is pressed.
  * @param callbackData                 (Optional) Data to be sent in a callback query when the button is pressed.
  * @param switchInlineQuery            (Optional) If set, pressing the button will prompt the user to select a chat, open that chat and insert the bot's username followed by the specified inline query in the input field.
  * @param switchInlineQueryCurrentChat (Optional) If set, pressing the button will insert the bot's username and the specified inline query in the current chat's input field.
  * @param pay                          (Optional) If true, a Pay button will be sent.
  * @param requestContact               (Optional) If true, the user's phone number will be sent as a contact when the button is pressed.
  * @param requestLocation              (Optional) If true, the user's current location will ne sent when the button is pressed.
  */
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

  /** Builds a [[ReplyKeyboardButton]] from this [[KeyboardButtonContainer]] */
  def toReplyKeyboardButton: ReplyKeyboardButton =
    ReplyKeyboardButton(text, requestContact, requestLocation)

  /** Builds an [[InlineKeyboardButton]] from this [[KeyboardButtonContainer]] */
  def toInlineKeyboardButton: InlineKeyboardButton =
    InlineKeyboardButton(text, url, callbackData, switchInlineQuery, switchInlineQueryCurrentChat, pay)
}
