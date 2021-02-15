package PPS19.scalagram.dsl.reactions.action

import PPS19.scalagram.dsl.reactions.ReactionCollector
import PPS19.scalagram.dsl.reactions.trigger.Trigger.Trigger
import PPS19.scalagram.dsl.reactions.trigger.TriggerList
import PPS19.scalagram.logic.{Bot, Context, Reaction}
import PPS19.scalagram.models.{
  InlineKeyboardMarkup,
  ReplyKeyboardMarkup,
  ReplyMarkup
}

object Action {

  val HTML = "HTML"
  val Markdown = "Markdown"
  val MarkdownV2 = "MarkdownV2"

  type Action = Context => Unit

  trait ActionObject extends ReactionCollector {
    def >>(action: Action): TriggerList
  }

  case class MessageBuilder private (
      message: String,
      parseMode: Option[String],
      keyboard: Option[Either[ReplyKeyboardMarkup, InlineKeyboardMarkup]]
  ) {

    def -(replyKeyboard: ReplyKeyboardMarkup): MessageBuilder =
      MessageBuilder(message, parseMode, Some(Left(replyKeyboard)))

    def -(inlineKeyboard: InlineKeyboardMarkup): MessageBuilder =
      MessageBuilder(message, parseMode, Some(Right(inlineKeyboard)))

    def -(parseMode: String): MessageBuilder =
      MessageBuilder(message, Some(parseMode), keyboard)
  }

  case class ActionObjectImpl(trigger: Trigger, reactions: List[Reaction])
      extends ActionObject {
    override def >>(action: Action): TriggerList =
      TriggerList(Bot.onCommand(trigger) { action } :: reactions)
  }

  object ActionConversions {
    implicit def sendMessageConversion(string: String): Action =
      context =>
        if (context.chat.nonEmpty) {
          context.bot.sendMessage(context.chat.get, string)
        }

    implicit def messageBuilderToAction(
        messageBuilder: MessageBuilder
    ): Action =
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

}
