package PPS19.scalagram.dsl.reaction

import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

/** Container used to build a Telegram message.
  *
  * @param message   Text of the message to be sent, 1-4096 characters after entities parsing.
  * @param parseMode Mode for parsing entities in the message text.
  *                  See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
  * @param keyboard  Keyboard to attach to the message
  */
case class MessageContainer(
    message: String,
    parseMode: Option[String],
    keyboard: Option[Either[ReplyKeyboardMarkup, InlineKeyboardMarkup]]
) {

  /** Creates a new container with [[message]] and [[parseMode]] from this one, plus the [[ReplyKeyboardMarkup]] given as a parameter.
    *
    * @param replyKeyboard Keyboard to attach to the message.
    */
  def -(replyKeyboard: ReplyKeyboardMarkup): MessageContainer =
    MessageContainer(message, parseMode, Some(Left(replyKeyboard)))

  /** Creates a new container with [[message]] and [[parseMode]] from this one, plus the [[InlineKeyboardMarkup]] given as a parameter.
    *
    * @param inlineKeyboard Keyboard to attach to the message.
    */
  def -(inlineKeyboard: InlineKeyboardMarkup): MessageContainer =
    MessageContainer(message, parseMode, Some(Right(inlineKeyboard)))
}
