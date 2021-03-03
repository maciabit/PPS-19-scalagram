package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.reactions.ReactionBuilder
import PPS19.scalagram.logic.{Context, Reaction}

case class PartialReactionContainer(
    reactions: List[Reaction],
    reactionBuilder: ReactionBuilder
) extends ReactionContainer {
  def >>(action: Context => Unit): TotalReactionContainer =
    TotalReactionContainer(reactions :+ reactionBuilder.build(action))
}
