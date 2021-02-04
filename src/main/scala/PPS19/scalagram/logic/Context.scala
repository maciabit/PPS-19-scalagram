package PPS19.scalagram.logic

import PPS19.scalagram.models.{Update, User}

import java.time.LocalDateTime
import scala.concurrent.duration.{DurationInt, FiniteDuration}

sealed trait Context {
  val bot: Bot
  val store: Map[String, Any]
  var timeout: FiniteDuration
  var lastUpdateTimestamp: LocalDateTime
  var activeScene: Option[Scene]
  var sceneStep: Option[Int]
  var update: Option[Update]
  var from: Option[User]
  var updateCount: Int
}

object Context {
  def apply(bot: Bot): Context = ContextImpl(bot)
  case class ContextImpl(bot: Bot) extends Context {
    override val store: Map[String, Any] = Map()
    override var timeout: FiniteDuration = 1.days
    override var lastUpdateTimestamp: LocalDateTime = LocalDateTime.now()
    override var activeScene: Option[Scene] = None
    override var sceneStep: Option[Int] = None
    override var update: Option[Update] = None
    override var from: Option[User] = None
    override var updateCount: Int = 0
  }
}