package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.reactions.{ReactionBuilder, VarArgReactionBuilder}
import PPS19.scalagram.logic.{Context, Reaction}

trait ReactionObject extends ReactionContainer {
  val reactions: List[Reaction]
  val reactionBuilder: ReactionBuilder
  def >>(action: Context => Unit): TriggerList =
    TriggerList(reactionBuilder.build(action) :: reactions)
}

object ReactionObject {
  def apply(
      reactions: List[Reaction],
      reactionBuilder: ReactionBuilder
  ): ReactionObject = ReactionObjectImpl(reactions, reactionBuilder)

  private case class ReactionObjectImpl(
      reactions: List[Reaction],
      reactionBuilder: ReactionBuilder
  ) extends ReactionObject
}

case class VarArgReactionObject(
    reactions: List[Reaction],
    reactionBuilder: VarArgReactionBuilder,
    triggers: String*
) extends ReactionObject {

  def ::(trigger: String): VarArgReactionObject = {
    val newTriggers = triggers :+ trigger
    VarArgReactionObject(
      reactions,
      reactionBuilder.fromStrings(newTriggers: _*),
      newTriggers: _*
    )
  }
}
