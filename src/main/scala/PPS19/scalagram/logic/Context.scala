package PPS19.scalagram.logic

import PPS19.scalagram.models.messages.TelegramMessage
import PPS19.scalagram.models.{ChatId, MessageUpdate, ReplyMarkup, Update, User}

import java.time.LocalDateTime
import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.util.{Failure, Try}

sealed trait Context {
  val bot: Bot
  val store: Map[String, Any]

  var timeout: FiniteDuration
  var lastUpdateTimestamp: LocalDateTime

  var activeScene: Option[Scene]
  var sceneStep: Option[Step]

  var update: Option[Update]
  def chat: Option[ChatId]
  def from: Option[User]
  def reply(
      text: String,
      parseMode: Option[String] = None,
      replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage]
  var updateCount: Int

  def enterScene(sceneName: String): Unit
  def leaveScene(): Unit

  def nextStep(): Unit
  def goToStep(index: Int): Unit
  def goToStep(stepName: String): Unit
}

object Context {
  def apply(bot: Bot): Context = ContextImpl(bot)

  case class ContextImpl(bot: Bot) extends Context {
    override val store: Map[String, Any] = Map()
    override var timeout: FiniteDuration = 1.days
    override var lastUpdateTimestamp: LocalDateTime = LocalDateTime.now()
    override var activeScene: Option[Scene] = None
    override var sceneStep: Option[Step] = None
    override var update: Option[Update] = None
    override var updateCount: Int = 0

    override def chat: Option[ChatId] =
      update match {
        case Some(update) => Some(update.asInstanceOf[MessageUpdate].chatId)
        case _            => None
      }

    override def from: Option[User] =
      update match {
        case Some(update) => update.asInstanceOf[MessageUpdate].from
        case _            => None
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
        case Some(chatId) =>
          bot.sendMessage(chatId, text, parseMode, replyMarkup = replyMarkup)
        case _ =>
          Failure(
            new IllegalStateException(
              "Cannot send message: context.chatId is None."
            )
          )
      }
  }
}
