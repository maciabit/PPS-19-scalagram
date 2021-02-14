package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.dsl.keyboard.TryKeyboard.keyboard
import PPS19.scalagram.logic.Context
import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

object Conversions {

  implicit def sendMessageConversion(string: String): Context => Unit =
    context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, string)}

  implicit def keyboardConversion(keyboard: ReplyKeyboardMarkup): Context => Unit =
    context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, "the k", replyMarkup = Some(keyboard))}

  implicit def inlineKeyboardConversion(keyboard: InlineKeyboardMarkup): Context => Unit =
    context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, "the k", replyMarkup = Some(keyboard))}

}
