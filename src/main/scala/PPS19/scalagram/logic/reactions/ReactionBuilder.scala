package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction}

trait ReactionBuilder {
  def build(action: Context => Unit): Reaction
}
