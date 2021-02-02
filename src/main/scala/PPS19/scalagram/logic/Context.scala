package PPS19.scalagram.logic

import PPS19.scalagram.models.{Update, User}

sealed trait Context {
  val bot: Bot
  val store: Map[String, Any]
  var activeScene: Option[Scene]
  var sceneStep: Option[Int]
  var update: Update
  var from: Option[User]
}

object Context {
  def apply(bot: Bot, update: Update): Context = ContextImpl(bot, update)
  case class ContextImpl(bot: Bot, var update: Update) extends Context {
    override var activeScene: Option[Scene] = None
    override val store: Map[String, Any] = Map()
    override var sceneStep: Option[Int] = None
    override var from: Option[User] = None
  }
}