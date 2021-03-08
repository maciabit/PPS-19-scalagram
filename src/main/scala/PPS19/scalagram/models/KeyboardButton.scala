package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

/** Defines a generic keyboard button.
  *
  * Used by [[PPS19.scalagram.models.InlineKeyboardButton]], [[PPS19.scalagram.models.ReplyKeyboardButton]],
  */
trait KeyboardButton {

  /** Label text on the button */
  val text: String
}

/** Represents one button of an inline keyboard (sent with the message). Exactly one of the optional fields must be used.
  *
  * @param text                         Label text on the button.
  * @param url                          (Optional) The url to be opened when the button is pressed.
  * @param callbackData                 (Optional) Data to be sent in a callback query when the button is pressed.
  * @param switchInlineQuery            (Optional) If set, pressing the button will prompt the user to select a chat, open that chat and insert the bot's username followed by the specified inline query in the input field.
  * @param switchInlineQueryCurrentChat (Optional) If set, pressing the button will insert the bot's username and the specified inline query in the current chat's input field.
  * @param pay                          (Optional) If true, a Pay button will be sent.
  *
  *                                     Extends [[PPS19.scalagram.models.KeyboardButton]].
  */
case class InlineKeyboardButton(
    text: String,
    url: Option[String] = None,
    callbackData: Option[String] = None,
    switchInlineQuery: Option[String] = None,
    switchInlineQueryCurrentChat: Option[String] = None,
    pay: Option[Boolean] = None
) extends KeyboardButton

/** Companion object for InlineKeyboardButton. Used as container for implicit methods and as a Factory. */
object InlineKeyboardButton {
  implicit val inlineKeyboardButtonEncoder: Encoder[InlineKeyboardButton] =
    deriveEncoder

  /** Creates an InlineKeyboardButton with the given URL that will be opened when the button is pressed.
    *
    * @param text Label text on the button.
    * @param url  The URL that will be opened.
    * @return the created InlineKeyboardButton.
    */
  def url(text: String, url: String): InlineKeyboardButton =
    InlineKeyboardButton(text, url = Some(url))

  /** Creates an InlineKeyboardButton with the given callback data that will be sent as callback query when the button is pressed.
    *
    * @param text         Label text on the button.
    * @param callbackData The data to be sent as callback.
    * @return the created InlineKeyboardButton.
    */
  def callback(text: String, callbackData: String): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackData = Some(callbackData))

  /** Creates an InlineKeyboardButton which will prompt the user to select a chat, open that chat and insert the bot's username followed by the specified inline query in the input field.
    *
    * @param text              Label text on the button.
    * @param switchInlineQuery The inline query inserted in the input field after the bot's username.
    * @return the created InlineKeyboardButton.
    */
  def switchInlineQuery(
      text: String,
      switchInlineQuery: String
  ): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQuery = Some(switchInlineQuery))

  /** Creates an InlineKeyboardButton which will insert the bot's username and the specified inline query in the current chat's input field.
    *
    * @param text                         Label text on the button.
    * @param switchInlineQueryCurrentChat The inline query inserted in the input field after the bot's username.
    * @return the created InlineKeyboardButton
    */
  def switchInlineQueryCurrentChat(
      text: String,
      switchInlineQueryCurrentChat: String
  ): InlineKeyboardButton =
    InlineKeyboardButton(
      text,
      switchInlineQueryCurrentChat = Some(switchInlineQueryCurrentChat)
    )

  /** Creates a Pay button.
    *
    * @param text Label text on the button.
    * @return the created InlineKeyboardButton.
    */
  def pay(text: String): InlineKeyboardButton =
    InlineKeyboardButton(text, pay = Some(true))
}

/** Represents one button of a reply keyboard (used to reply as an alternative to the classic input field). Optional fields are mutually exclusive.
  *
  * @param text            Text of the button.
  * @param requestContact  (Optional) If true, the user's phone number will be sent as a contact when the button is pressed.
  * @param requestLocation (Optional) If true, the user's current location will ne sent when the button is pressed.
  *
  *                        Extends [[PPS19.scalagram.models.KeyboardButton]].
  */
case class ReplyKeyboardButton(
    text: String,
    requestContact: Option[Boolean] = None,
    requestLocation: Option[Boolean] = None
) extends KeyboardButton

/** Companion object for ReplyKeyboardButton. Used as container for implicit methods.*/
object ReplyKeyboardButton {
  implicit val keyboardButtonEncoder: Encoder[ReplyKeyboardButton] =
    deriveEncoder
}
