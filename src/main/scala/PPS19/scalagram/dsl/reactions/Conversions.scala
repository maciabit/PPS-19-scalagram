package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.Context

object Conversions {

  implicit def sendMessageConversion(string: String): Context => Unit =
    context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, string)}

}
