package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction}

/** A reaction that only gets executed if the update is a "/help" message. */
case class OnHelp() extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction = OnMessage("/help").build(action)
}
