package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.middleware.MiddlewareContainer
import PPS19.scalagram.dsl.mode.WorkingMode
import PPS19.scalagram.dsl.mode.WorkingMode.WorkingMode
import PPS19.scalagram.dsl.reactions.{ReactionContainer, ReactionObject}
import PPS19.scalagram.dsl.scenes.SceneContainer
import PPS19.scalagram.logic._
import PPS19.scalagram.logic.reactions.OnStart
import PPS19.scalagram.modes.polling.{Mode, Polling}

trait TelegramBotDSL {

  private var _token: BotToken = _
  private var _bot: Bot = _

  private var _mode: Mode = _
  private var _middlewares: List[Middleware] = Nil
  private var _reactions: List[Reaction] = Nil
  private var _scenes: List[Scene] = Nil

  protected def token(string: String): Unit = {
    _token = BotToken(string)
  }

  protected def mode(mode: WorkingMode): Unit = {
    _mode = mode match {
      case WorkingMode.Polling(interval, delay) =>
        Polling(interval, delay)
    }
  }

  protected def reactions(reactionContainer: ReactionContainer): Unit = {
    _reactions = reactionContainer.reactions
  }

  protected def middlewares(middlewareContainer: MiddlewareContainer): Unit = {
    _middlewares = middlewareContainer.middlewares
  }

  protected def scenes(sceneContainer: SceneContainer): Unit = {
    _scenes = sceneContainer.scenes
  }

  protected def !! : ReactionObject = ReactionObject(Nil, OnStart())

  protected def <->(
      middleware: Context => Boolean
  ): MiddlewareContainer =
    MiddlewareContainer(Middleware(middleware) :: Nil)

  def start(): Unit = {
    _bot = Bot(_token, _middlewares, _reactions)
    _mode.start(_bot)
  }
}
