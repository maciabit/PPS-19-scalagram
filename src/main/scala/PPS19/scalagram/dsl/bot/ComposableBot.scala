package PPS19.scalagram.dsl.bot

import PPS19.scalagram.dsl.bot.WorkingMode.WorkingMode
import PPS19.scalagram.dsl.reactions.{ActionObject, ReactionContainer}
import PPS19.scalagram.logic._
import PPS19.scalagram.modes.polling.Mode

trait ComposableBot {

  private var _token: BotToken = _
  private var _bot: Bot = _
  private var _context: Context = _

  private var _mode: Mode = _
  private var _reactions: List[Reaction] = Nil
  private var _middlewares: List[Middleware] = Nil

  protected def token(string: String): Unit = {_token = BotToken{string}}

  protected def mode(mode: WorkingMode): Unit = {_mode = mode match {
    case WorkingMode.Polling(interval,delay,debug) => PPS19.scalagram.modes.polling.Polling(interval, delay, debug)
  }}

  protected def reactions(reactionContainer: ReactionContainer): Unit = {_reactions = reactionContainer.reactions}

  protected def middlewares(middlewares: Middleware*): Unit = {_middlewares = middlewares.toList}


  protected def in(trigger: String): ActionObject = ActionObject(trigger, Nil)
  protected def <->(middleware: Context => Boolean): Middleware = Middleware(middleware)



  def start(): Unit = {
    _bot = Bot(_token, reactions = _reactions, middlewares = _middlewares)
    _context = Context{_bot}
    _mode.start(_bot)
  }
}
