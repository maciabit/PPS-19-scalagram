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

  type Action = Context => Unit

  trait ActionObject extends ReactionCollector {
    def >>(action: Action): TriggerList
  }

  def HTML(message: String): MessageBuilder =
    MessageBuilder(message, Some("HTML"), None)

  def Markdown(message: String): MessageBuilder =
    MessageBuilder(message, Some("Markdown"), None)

  def MarkdownV2(message: String): MessageBuilder =
    MessageBuilder(message, Some("MarkdownV2"), None)

  case class MessageBuilder protected[Action] (
      message: String,
      parseMode: Option[String],
      keyboard: Option[Either[ReplyKeyboardMarkup, InlineKeyboardMarkup]]
  ) {

    def -(replyKeyboard: ReplyKeyboardMarkup): MessageBuilder =
      MessageBuilder(message, parseMode, Some(Left(replyKeyboard)))

    def -(inlineKeyboard: InlineKeyboardMarkup): MessageBuilder =
      MessageBuilder(message, parseMode, Some(Right(inlineKeyboard)))

  }

  case class ActionObjectImpl(trigger: Trigger, reactions: List[Reaction])
      extends ActionObject {
    override def >>(action: Action): TriggerList =
      TriggerList(Bot.onMessage(trigger) { action } :: reactions)
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
