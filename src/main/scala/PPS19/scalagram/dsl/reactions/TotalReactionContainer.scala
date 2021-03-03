package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.dsl.Star
import PPS19.scalagram.logic.Reaction
import PPS19.scalagram.logic.reactions._

case class TotalReactionContainer(reactions: List[Reaction]) extends ReactionContainer {

  //def <@(): PartialReactionContainer = PartialReactionContainer(reactions, OnHelp())

  def <<(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnMessage())
  }

  def <<(trigger: String): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage(trigger), trigger)

  def <<(triggers: List[String]): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessage(triggers: _*), triggers: _*)

  def <~(trigger: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnCallbackQuery(trigger))

  def <*(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnMessageEdited())
  }

  def <*(trigger: String): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited(trigger), trigger)

  def <*(triggers: List[String]): VarArgReactionContainer =
    VarArgReactionContainer(reactions, OnMessageEdited(triggers: _*), triggers: _*)

  def <#(regex: String): PartialReactionContainer = PartialReactionContainer(reactions, OnMatch(regex))

  def <^(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnMessagePinned())
  }

  def <+(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnChatEnter())
  }

  def </(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnChatLeave())
  }

}
