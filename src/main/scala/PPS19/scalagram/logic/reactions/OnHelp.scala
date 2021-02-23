package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction}

case class OnHelp() extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction = OnMessage("/help").build(action)
}
