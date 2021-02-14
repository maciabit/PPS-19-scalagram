package PPS19.scalagram.dsl.reactions.trigger

import PPS19.scalagram.dsl.reactions.ReactionCollector
import PPS19.scalagram.dsl.reactions.action.Action.{ActionObject, ActionObjectImpl}
import PPS19.scalagram.dsl.reactions.trigger.Trigger.Trigger
import PPS19.scalagram.logic.Reaction

case class TriggerList(reactions: List[Reaction]) extends ReactionCollector {

  def <<(trigger: Trigger): ActionObject = ActionObjectImpl(trigger, reactions)
  def <~(trigger: Trigger): ActionObject = ActionObjectImpl(trigger, reactions)

  private def createActionObject(trigger: Trigger): ActionObject = ActionObjectImpl(trigger, reactions)
}
