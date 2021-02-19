package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.dsl.reactions.ReactionUtils.MessageContainer
import PPS19.scalagram.logic.Context

object ReactionConversions {
  implicit def stringToSendMessage(string: String): Context => Unit =
    context =>
      if (context.chat.nonEmpty) {
        context.bot.sendMessage(context.chat.get, string)
      }

  implicit def messageBuilderToAction(
      messageBuilder: MessageContainer
  ): Context => Unit =
    context =>
      if (context.chat.nonEmpty) {
        context.bot.sendMessage(
          context.chat.get,
          messageBuilder.message,
          messageBuilder.parseMode,
          replyMarkup = messageBuilder.keyboard match {
            case Some(Left(replyKeyboardMarkup)) => Some(replyKeyboardMarkup)
            case Some(Right(inlineKeyboardMarkup)) =>
              Some(inlineKeyboardMarkup)
            case _ => None
          }
        )
      }
}
