package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.dsl.reactions.Action.Action
import PPS19.scalagram.logic.{Bot, Context, Reaction, Trigger}

trait ReactionContainer {def reactions: List[Reaction]}

case class TriggerObject(reactions: List[Reaction]) extends ReactionContainer {
  def <<(trigger: String): ActionObject = ActionObject(trigger, reactions)
}

case class ActionObject(private val trigger: String, reactions: List[Reaction]) extends ReactionContainer {
  def >>(action: Action): TriggerObject = TriggerObject(Bot.onCommand(trigger){action} :: reactions)
}

