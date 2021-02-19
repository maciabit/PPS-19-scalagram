package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.Reaction

trait ReactionContainer {
  def reactions: List[Reaction]
}
