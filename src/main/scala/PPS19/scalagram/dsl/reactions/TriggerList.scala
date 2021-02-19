package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.Reaction
import PPS19.scalagram.logic.reactions._

case class TriggerList(reactions: List[Reaction]) extends ReactionContainer {

  def ?? : ReactionObject = ReactionObject(reactions, OnHelp())

  def <<(trigger: String): VarArgReactionObject =
    VarArgReactionObject(reactions, OnMessage(trigger), trigger)

  def <<(triggers: List[String]): VarArgReactionObject =
    VarArgReactionObject(reactions, OnMessage(triggers: _*), triggers:_*)

  def <~(trigger: String): ReactionObject =
    ReactionObject(reactions, OnCallbackQuery(trigger))

  def <*(trigger: String): VarArgReactionObject =
    VarArgReactionObject(reactions, OnMessageEdited(trigger), trigger)

  def <^ : ReactionObject = ReactionObject(reactions, OnMessagePinned())

  def <+ : ReactionObject = ReactionObject(reactions, OnChatEnter())

  def :> : ReactionObject = ReactionObject(reactions, OnChatLeave())

  def <#(regex: String): ReactionObject =
    ReactionObject(reactions, OnMatch(regex))

}
