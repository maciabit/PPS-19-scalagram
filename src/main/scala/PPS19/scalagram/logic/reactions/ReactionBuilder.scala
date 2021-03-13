package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction}

/** A factory for the [[Reaction]] class. */
trait ReactionBuilder {

  /** Creates a reaction for the given action function. */
  def build(action: Context => Unit): Reaction
}
