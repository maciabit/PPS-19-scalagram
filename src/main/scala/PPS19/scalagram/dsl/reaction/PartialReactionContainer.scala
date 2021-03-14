package PPS19.scalagram.dsl.reaction

import PPS19.scalagram.logic.reactions.ReactionBuilder
import PPS19.scalagram.logic.{Context, Reaction}

/** Container that has list of reactions and a partially defined reaction.
  * A [[PartialReactionContainer]] cannot be passed to a bot's [[PPS19.scalagram.dsl.ScalagramDSL.reactions]] method,
  * because it includes a reaction that is not totally defined.
  * A partially defined reaction can be terminated by concatenating an action using the [[>>]] method.
  *
  * @param reactions       Reactions to include in the container
  * @param reactionBuilder Builder for the partially defined reaction
  *
  *                        Extends [[ReactionContainer]].
  */
case class PartialReactionContainer(
    reactions: List[Reaction],
    reactionBuilder: ReactionBuilder
) extends ReactionContainer {

  /** Creates a [[TotalReactionContainer]] with the actions from this container, plus a new one obtained by combining [[reactionBuilder]] and the given action
    *
    * @param action Function to be executed by the new reaction
    */
  def >>(action: Context => Unit): TotalReactionContainer =
    TotalReactionContainer(reactions :+ reactionBuilder.build(action))
}
