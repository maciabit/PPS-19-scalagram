package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.middleware.{
  MiddlewareContainer,
  MiddlewareContainerConcatenation
}
import PPS19.scalagram.dsl.mode.WorkingMode
import PPS19.scalagram.dsl.mode.WorkingMode.WorkingMode
import PPS19.scalagram.dsl.reactions.ReactionCollector
import PPS19.scalagram.dsl.reactions.action.Action.{
  ActionObject,
  ActionObjectImpl
}
import PPS19.scalagram.logic._
import PPS19.scalagram.modes.polling.{Mode, Polling}

trait TelegramBotDSL {

  private var _token: BotToken = _
  private var _bot: Bot = _

  private var _mode: Mode = _
  private var _middlewares: List[Middleware] = Nil
  private var _reactions: List[Reaction] = Nil

  protected def token(string: String): Unit = {
    _token = BotToken(string)
  }

  protected def mode(mode: WorkingMode): Unit = {
    _mode = mode match {
      case WorkingMode.Polling(interval, delay) =>
        Polling(interval, delay)
    }
  }

  protected def reactions(reactionContainer: ReactionCollector): Unit = {
    _reactions = reactionContainer.reactions
  }

  protected def middlewares(middlewareContainer: MiddlewareContainer): Unit = {
    _middlewares = middlewareContainer.middlewares
  }

  protected def |> : ActionObject = ActionObjectImpl("/start", Nil)

  protected def <->(
      middleware: Context => Boolean
  ): MiddlewareContainerConcatenation =
    MiddlewareContainerConcatenation(Middleware(middleware) :: Nil)

  def start(): Unit = {
    _bot = Bot(_token, _middlewares, _reactions)
    _mode.start(_bot)
  }
}
