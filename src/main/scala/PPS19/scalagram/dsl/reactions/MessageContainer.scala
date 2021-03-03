package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

case class MessageContainer(
    message: String,
    parseMode: Option[String],
    keyboard: Option[Either[ReplyKeyboardMarkup, InlineKeyboardMarkup]]
) {

  def -(replyKeyboard: ReplyKeyboardMarkup): MessageContainer =
    MessageContainer(message, parseMode, Some(Left(replyKeyboard)))

  def -(inlineKeyboard: InlineKeyboardMarkup): MessageContainer =
    MessageContainer(message, parseMode, Some(Right(inlineKeyboard)))
}
