package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.Context
import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup, ReplyMarkup}

object Action {

  type Action = Context => Unit

  implicit def sendMessageConversion(string: String): Action =
    context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, string)}

  implicit def keyboardConversion(keyboard: ReplyMarkup): Action =
    context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, "the k", replyMarkup = Some(keyboard))}

}
