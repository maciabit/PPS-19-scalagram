package PPS19.scalagram.dsl.reactions.action

import PPS19.scalagram.dsl.reactions.ReactionCollector
import PPS19.scalagram.dsl.reactions.trigger.Trigger.Trigger
import PPS19.scalagram.dsl.reactions.trigger.TriggerList
import PPS19.scalagram.logic.{Bot, Context, Reaction}
import PPS19.scalagram.models.ReplyMarkup

object Action {

  type Action = Context => Unit

  trait ActionObject extends ReactionCollector {def >>(action: Action): TriggerList}

  case class ActionObjectImpl(trigger: Trigger, reactions: List[Reaction]) extends ActionObject {
    override def >>(action: Action): TriggerList = TriggerList(Bot.onCommand(trigger){action} :: reactions)
  }

  object ActionConversions {
    implicit def sendMessageConversion(string: String): Action =
      context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, string)}

    implicit def keyboardConversion(keyboard: ReplyMarkup): Action =
      context => if (context.chat.nonEmpty) {context.bot.sendMessage(context.chat.get, "the k", replyMarkup = Some(keyboard))}
  }

}
