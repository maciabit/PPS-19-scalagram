package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.middleware.MiddlewareContainer
import PPS19.scalagram.dsl.mode.{PollingModeContainer, WorkingModeContainer}
import PPS19.scalagram.dsl.reaction.{PartialReactionContainer, TotalReactionContainer}
import PPS19.scalagram.dsl.scene.{SceneContainer, TotalStepContainer}
import PPS19.scalagram.logic._
import PPS19.scalagram.logic.reactions.OnStart
import PPS19.scalagram.logic.scenes.Scene
import PPS19.scalagram.models.BotToken
import PPS19.scalagram.modes.polling.Mode

/** Container used to build a bot using the DSL.
  *
  * Used by [[PPS19.scalagram.examples.dsl.CommandsBot]], [[PPS19.scalagram.examples.dsl.KeyboardsBot]], [[PPS19.scalagram.examples.dsl.ScenesBot]], [[PPS19.scalagram.examples.dsl.SimpleDSLBot]]
  */
trait ScalagramDSL {

  protected[dsl] var _token: BotToken = _
  protected[dsl] var _bot: Scalagram = _

  protected[dsl] var _mode: Mode = _
  protected[dsl] var _middlewares: List[Middleware] = Nil
  protected[dsl] var _reactions: List[Reaction] = Nil
  protected[dsl] var _scenes: List[Scene] = Nil

  /** Method used to set the bot token.
    *
    * @param string The bot token.
    */
  protected def token(string: String): Unit = {
    _token = BotToken(string)
  }

  /** Method used to set the bot working mode.
    *
    * @param mode The container of the working mode.
    */
  protected def mode(mode: WorkingModeContainer): Unit = {
    _mode = mode match {
      case PollingModeContainer(interval, delay) => PPS19.scalagram.modes.polling.Polling(interval, delay)
    }
  }

  /** Method used to set the bot reactions.
    *
    * @param reactionContainer The container of all reactions.
    */
  protected def reactions(reactionContainer: TotalReactionContainer): Unit = {
    _reactions = reactionContainer.reactions
  }

  /** Method used to set the bot middlewares.
    *
    * @param middlewareContainer The container of all middlewares.
    */
  protected def middlewares(middlewareContainer: MiddlewareContainer): Unit = {
    _middlewares = middlewareContainer.middlewares
  }

  /** Method used to set the bot reactions.
    *
    * @param sceneContainer The container of all scenes.
    */
  protected def scenes(sceneContainer: SceneContainer): Unit = {
    _scenes = sceneContainer.scenes
  }

  /** Creates a [[PartialReactionContainer]] with [[OnStart]] reaction,  which will always be the first reaction of the bot.
    * Used to start the creation of the [[TotalReactionContainer]], that will be obtained by using the [[PartialReactionContainer.>>()]] method.
    *
    * @return The [[PartialReactionContainer]] with the [[OnStart]] reaction.
    */
  protected def !! : PartialReactionContainer = PartialReactionContainer(Nil, OnStart())

  /** Creates a [[MiddlewareContainer]], used to contain a [[Middleware]] and concatenate them.
    *
    * @param middleware The middleware that will be created.
    * @return The created [[MiddlewareContainer]].
    */
  protected def <>(middleware: Context => Boolean): MiddlewareContainer =
    MiddlewareContainer(Middleware(middleware) :: Nil)

  /** Creates a [[SceneContainer]], used to contain a [[Scene]] and concatenate them.
    *
    * @param stepContainer The [[TotalStepContainer]] containing the list of all scene's steps.
    * @return The created [[SceneContainer]].
    */
  protected def scene(stepContainer: TotalStepContainer): SceneContainer =
    SceneContainer(Scene(stepContainer.sceneName, stepContainer.steps) :: Nil)

  protected[dsl] def build(): Unit = {
    _bot = Scalagram(_token, _middlewares, _reactions, _scenes)
  }

  /** Method used to start the bot. */
  def start(): Unit = {
    build()
    _mode.start(_bot)
  }
}
