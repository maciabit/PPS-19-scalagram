package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.Reaction
import PPS19.scalagram.logic.reactions._

case class TotalReactionContainer(reactions: List[Reaction]) extends ReactionContainer {

  def ?? : PartialReactionContainer = PartialReactionContainer(reactions, OnHelp())

  // TODO add parameterless versions of << and <*

  def <<(trigger: String): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage(trigger), trigger)

  def <<(triggers: List[String]): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage(triggers: _*), triggers: _*)

  def <~(trigger: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnCallbackQuery(trigger))

  def <*(trigger: String): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited(trigger), trigger)

  def <*(triggers: List[String]): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited(triggers: _*), triggers: _*)

  def <^ : PartialReactionContainer = PartialReactionContainer(reactions, OnMessagePinned())

  def <+ : PartialReactionContainer = PartialReactionContainer(reactions, OnChatEnter())

  def :> : PartialReactionContainer = PartialReactionContainer(reactions, OnChatLeave())

  def <#(regex: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnMatch(regex))

}
