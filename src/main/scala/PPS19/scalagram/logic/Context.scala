package PPS19.scalagram.logic

import PPS19.scalagram.models._
import PPS19.scalagram.models.payloads.TelegramMessage
import PPS19.scalagram.models.updates.{ChatUpdate, MessageUpdate, Update}

import java.time.LocalDateTime
import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.util.{Failure, Try}

/** Execution context of a bot.
  * There is exactly one instance of [[Context]] for each pair of [[Bot]] and [[Chat]].
  */
trait Context {

  /** Bot instance that this context refers to. */
  val bot: Bot

  /** Map that can be used to store context specific data. */
  var store: Map[String, Any]

  /** Timeout that must pass without receiving any update for the context to get destroyed. */
  var timeout: FiniteDuration

  /** Timestamp of the last update. */
  var lastUpdateTimestamp: LocalDateTime

  /** Active scene. */
  var activeScene: Option[Scene]

  /** Active scene step. */
  var sceneStep: Option[Step]

  /** Current update. */
  var update: Option[Update]

  /** Chat that this context refers to */
  def chat: Option[Chat]

  /** User that triggered the last update */
  def from: Option[User]

  /** Shortcut to send a message in the context chat.
    *
    * @param text        Text of the message to be sent, 1-4096 characters after entities parsing.
    * @param parseMode   Mode for parsing entities in the message text.
    *                    See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
    * @param replyMarkup Additional interface options.
    *                    Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
    *                    [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
    * @return
    */
  def reply(
      text: String,
      parseMode: Option[String] = None,
      replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage]

  /** Number of processed updates since the context was created. */
  var updateCount: Int

  /** Enters a scene.
    *
    * @param sceneName Name of the scene to activate.
    */
  def enterScene(sceneName: String): Unit

  /** Leaves the currently active scene. */
  def leaveScene(): Unit

  /** Moves to the next step of the currently active scene. */
  def nextStep(): Unit

  /** Activates a step of the currently active scene.
    *
    * @param index Index of the step to activate.
    */
  def goToStep(index: Int): Unit

  /** Activates a step of the currently active scene.
    *
    * @param stepName Name of the step to activate.
    */
  def goToStep(stepName: String): Unit
}

/** Companion object for the Context trait. */
object Context {

  /** Creates a new context for the given bot instance. */
  def apply(bot: Bot): Context = ContextImpl(bot)

  private case class ContextImpl(bot: Bot) extends Context {
    override var store: Map[String, Any] = Map()
    override var timeout: FiniteDuration = 1.days
    override var lastUpdateTimestamp: LocalDateTime = LocalDateTime.now()
    override var activeScene: Option[Scene] = None
    override var sceneStep: Option[Step] = None
    override var update: Option[Update] = None
    override var updateCount: Int = 0

    override def chat: Option[Chat] =
      update match {
        case Some(update: ChatUpdate) => Some(update.chat)
        case _                        => None
      }

    override def from: Option[User] =
      update match {
        case Some(update: MessageUpdate) => update.from
        case _                           => None
      }

    private def sceneStepIndex: Option[Int] =
      activeScene.map(_.steps.indexWhere(_ == sceneStep.orNull)) match {
        case Some(i) => if (i != -1) Some(i) else None
        case None    => None
      }

    override def enterScene(sceneName: String): Unit = {
      activeScene = bot.scenes.find(_.name == sceneName)
      sceneStep = activeScene.map(_.steps.head)
    }

    override def leaveScene(): Unit = {
      activeScene = None
      sceneStep = None
    }

    override def nextStep(): Unit =
      sceneStep = sceneStepIndex match {
        case Some(i) if i != activeScene.get.steps.length - 1 =>
          Some(activeScene.get.steps(i + 1))
        case _ => None
      }

    override def goToStep(index: Int): Unit =
      sceneStep = activeScene match {
        case Some(scene) => Some(scene.steps(index))
        case _           => None
      }

    override def goToStep(stepName: String): Unit =
      sceneStep = activeScene match {
        case Some(scene) => scene.steps.find(_.name == stepName)
        case _           => None
      }

    override def reply(
        text: String,
        parseMode: Option[String] = None,
        replyMarkup: Option[ReplyMarkup] = None
    ): Try[TelegramMessage] =
      chat match {
        case Some(chat) =>
          bot.sendMessage(
            chat.chatId,
            text,
            parseMode,
            replyMarkup = replyMarkup
          )
        case _ =>
          Failure(
            new IllegalStateException(
              "Cannot send message: context.chatId is None."
            )
          )
      }
  }
}
