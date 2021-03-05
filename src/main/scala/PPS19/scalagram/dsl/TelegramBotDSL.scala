package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.middleware.MiddlewareContainer
import PPS19.scalagram.dsl.mode.{PollingModeContainer, WorkingModeContainer}
import PPS19.scalagram.dsl.reactions.{PartialReactionContainer, TotalReactionContainer}
import PPS19.scalagram.dsl.scenes.SceneContainer
import PPS19.scalagram.dsl.scenes.steps.TotalStepContainer
import PPS19.scalagram.logic._
import PPS19.scalagram.logic.reactions.OnStart
import PPS19.scalagram.modes.polling.Mode

trait TelegramBotDSL {

  protected[dsl] var _token: BotToken = _
  protected[dsl] var _bot: Bot = _

  protected[dsl] var _mode: Mode = _
  protected[dsl] var _middlewares: List[Middleware] = Nil
  protected[dsl] var _reactions: List[Reaction] = Nil
  protected[dsl] var _scenes: List[Scene] = Nil

  protected def token(string: String): Unit = {
    _token = BotToken(string)
  }

  protected def mode(mode: WorkingModeContainer): Unit = {
    _mode = mode match {
      case PollingModeContainer(interval, delay) => PPS19.scalagram.modes.polling.Polling(interval, delay)
    }
  }

  protected def reactions(reactionContainer: TotalReactionContainer): Unit = {
    _reactions = reactionContainer.reactions
  }

  protected def middlewares(middlewareContainer: MiddlewareContainer): Unit = {
    _middlewares = middlewareContainer.middlewares
  }

  protected def scenes(sceneContainer: SceneContainer): Unit = {
    _scenes = sceneContainer.scenes
  }

  protected def !! : PartialReactionContainer = PartialReactionContainer(Nil, OnStart())

  protected def <>(middleware: Context => Boolean): MiddlewareContainer =
    MiddlewareContainer(Middleware(middleware) :: Nil)

  protected def scene(stepContainer: TotalStepContainer): SceneContainer =
    SceneContainer(Scene(stepContainer.sceneName, stepContainer.steps) :: Nil)

  protected[dsl] def build(): Unit = {
    _bot = Bot(_token, _middlewares, _reactions, _scenes)
  }

  def start(): Unit = {
    build()
    _mode.start(_bot)
  }
}
