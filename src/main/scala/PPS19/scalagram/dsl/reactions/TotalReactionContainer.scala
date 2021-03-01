package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.logic.Reaction
import PPS19.scalagram.logic.reactions._

case class TotalReactionContainer(reactions: List[Reaction]) extends ReactionContainer {

  //def <@(): PartialReactionContainer = PartialReactionContainer(reactions, OnHelp())

  /*def <<(): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage())*/

  def <<(trigger: String): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage(trigger), trigger)

  def <<(triggers: List[String]): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage(triggers: _*), triggers: _*)

  def <~(trigger: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnCallbackQuery(trigger))

  def <*(): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited())

  def <*(trigger: String = ""): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited(trigger), trigger)

  def <*(triggers: List[String]): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited(triggers: _*), triggers: _*)

  def <#(regex: String): PartialReactionContainer = PartialReactionContainer(reactions, OnMatch(regex))

  /*def <^(): PartialReactionContainer = PartialReactionContainer(reactions, OnMessagePinned())

  def <+(): PartialReactionContainer = PartialReactionContainer(reactions, OnChatEnter())

  def </(): PartialReactionContainer = PartialReactionContainer(reactions, OnChatLeave())*/

}
