package PPS19.scalagram.dsl.reaction

import PPS19.scalagram.logic.Reaction

/** Container used to build a list of reactions */
trait ReactionContainer {

  /** List of [[PPS19.scalagram.logic.Reaction]] to put inside of this container */
  def reactions: List[Reaction]
}
