package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.reactions.{ReactionBuilder, VarArgReactionBuilder}
import PPS19.scalagram.logic.{Context, Reaction}

trait PartialReactionContainer extends ReactionContainer {
  val reactions: List[Reaction]
  val reactionBuilder: ReactionBuilder
  def >>(action: Context => Unit): TotalReactionContainer =
    TotalReactionContainer(reactionBuilder.build(action) :: reactions)
}

object PartialReactionContainer {
  def apply(
      reactions: List[Reaction],
      reactionBuilder: ReactionBuilder
  ): PartialReactionContainer = PartialReactionContainerImpl(reactions, reactionBuilder)

  private case class PartialReactionContainerImpl(
      reactions: List[Reaction],
      reactionBuilder: ReactionBuilder
  ) extends PartialReactionContainer
}

case class VarArgReactionContainer(
    reactions: List[Reaction],
    reactionBuilder: VarArgReactionBuilder,
    triggers: String*
) extends PartialReactionContainer {

  def ::(trigger: String): VarArgReactionContainer = {
    val newTriggers = triggers :+ trigger
    VarArgReactionContainer(
      reactions,
      reactionBuilder.fromStrings(newTriggers: _*),
      newTriggers: _*
    )
  }
}
