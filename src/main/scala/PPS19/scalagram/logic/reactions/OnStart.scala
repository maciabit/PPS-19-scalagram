package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction}

/** A reaction that only gets executed if the update is a "/start" message. */
case class OnStart() extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction = OnMessage("/start").build(action)
}
