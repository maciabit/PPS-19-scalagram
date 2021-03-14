package PPS19.scalagram.dsl.reaction

import PPS19.scalagram.dsl.Star
import PPS19.scalagram.logic.Reaction
import PPS19.scalagram.logic.reactions._

/** Container that has list of reactions.
  * A [[TotalReactionContainer]] can be passed to a bot's [[PPS19.scalagram.dsl.ScalagramDSL.reactions]] method.
  * To concatenate another action, this container offers various methods depending on the type of action, such as [[<<(trigger:String)*]]
  *
  * @param reactions Reactions to include in the container
  *
  * Extends [[ReactionContainer]].
  */
case class TotalReactionContainer(reactions: List[Reaction]) extends ReactionContainer {

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessage]] reaction that matches any message.
    *
    * @param * Match any message
    */
  def <<(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnMessage())
  }

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessage]] reaction that matches a single message.
    *
    * @param trigger Match messages that are equal to this string
    */
  def <<(trigger: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnMessage(trigger))

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessage]] reaction that matches a list of messages.
    *
    * @param triggers Match messages that are equal to one of this strings
    */
  def <<(triggers: List[String]): PartialReactionContainer =
    PartialReactionContainer(reactions, OnMessage(triggers: _*))

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnCallbackQuery]] reaction.
    *
    * @param trigger Match callbacks with data equal to this string
    */
  def <~(trigger: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnCallbackQuery(trigger))

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessageEdited]] reaction that matches any message.
    *
    * @param * Match any edited message
    */
  def <*(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnMessageEdited())
  }

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessageEdited]] reaction that matches a single message.
    *
    * @param trigger Match edited messages that are equal to this string
    */
  def <*(trigger: String): PartialReactionContainer =
    PartialReactionContainer(reactions, OnMessageEdited(trigger))

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessageEdited]] reaction that matches a list of messages.
    *
    * @param triggers Match edited messages that are equal to one of this strings
    */
  def <*(triggers: List[String]): PartialReactionContainer =
    PartialReactionContainer(reactions, OnMessageEdited(triggers: _*))

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMatch]] reaction.
    *
    * @param regex Match messages that pass this regular expression
    */
  def <#(regex: String): PartialReactionContainer = PartialReactionContainer(reactions, OnMatch(regex))

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnMessagePinned]] reaction.
    *
    * @param * Match any pinned message
    */
  def <^(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnMessagePinned())
  }

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnChatEnter]] reaction.
    *
    * @param * Execute this reaction when a new user enters a chat.
    */
  def <+(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnChatEnter())
  }

  /** Creates a [[PartialReactionContainer]] with all the reactions from this container,
    * plus a partial [[PPS19.scalagram.logic.reactions.OnChatLeave]] reaction.
    *
    * @param * Execute this reaction when a user leaves a chat.
    */
  def </(* : Star): PartialReactionContainer = {
    *.s()
    PartialReactionContainer(reactions, OnChatLeave())
  }

}
