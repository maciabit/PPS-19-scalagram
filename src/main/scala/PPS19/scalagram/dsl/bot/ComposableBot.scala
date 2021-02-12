package PPS19.scalagram.dsl.bot

import WorkingMode.{Polling, WorkingMode}
import PPS19.scalagram.dsl.reactions.{ActionObject, Reaction, ReactionContainer}
import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.modes.polling.{Mode, Polling}

import scala.concurrent.duration.FiniteDuration

trait ComposableBot {

  private var _token: BotToken = _
  private var _mode: Mode = _
  private var _reactions: List[Reaction] = _ // CHANGE THE REACTION!

  def token(string: String): Unit = {_token = BotToken(string)}

  def mode(mode: WorkingMode): Unit = {_mode = mode match {
    case WorkingMode.Polling(interval,delay,debug) => PPS19.scalagram.modes.polling.Polling()
  }}

  def reactions(reactionContainer: ReactionContainer): Unit = {_reactions = reactionContainer.reactions}

  def in(trigger: String): ActionObject = ActionObject(trigger, Nil)


  //def middlewares()

  //def start(): Unit = {_mode.start(this)}




}
