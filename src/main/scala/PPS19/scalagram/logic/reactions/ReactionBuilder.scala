package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction}

trait ReactionBuilder {
  def build(action: Context => Unit): Reaction
}

trait SingleArgReactionBuilder extends ReactionBuilder {
  def fromString(string: String): SingleArgReactionBuilder
}

trait VarArgReactionBuilder extends ReactionBuilder {
  def fromStrings(strings: String*): VarArgReactionBuilder
}