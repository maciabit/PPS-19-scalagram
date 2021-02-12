package PPS19.scalagram.dsl.reactions

trait ReactionContainer {def reactions: List[Reaction]}

case class TriggerObject(reactions: List[Reaction]) extends ReactionContainer {
  def <<(trigger: String): ActionObject = ActionObject(trigger, reactions)
}

case class ActionObject(private val trigger: String, reactions: List[Reaction]) extends ReactionContainer {
  def >>(action: String): TriggerObject = TriggerObject(Reaction(trigger, action) :: reactions)
}

case class Reaction(trigger: String, action: String) // PLACEHOLDER for the real reaction, trigger and action


