package PPS19.scalagram.logic

import PPS19.scalagram.models.Update

case class BotToken(token: String)

sealed trait Bot {
  val token: BotToken
  val middlewares: List[Middleware]
  val scenes: List[Scene]
  val reactions: List[Reaction]
  var context: Context
  def receiveUpdate(update: Update): Unit
}

object Bot {

  def apply(
    token: BotToken,
    middlewares: List[Middleware],
    scenes: List[Scene],
    reactions: List[Reaction]
  ): Bot = BotImpl(token, middlewares, scenes, reactions)

  def unapply(bot: Bot): Option[(BotToken, List[Middleware], List[Scene], List[Reaction], Context)] =
    Some(bot.token, bot.middlewares, bot.scenes, bot.reactions, bot.context)

  case class BotImpl (
    token: BotToken,
    middlewares: List[Middleware],
    scenes: List[Scene],
    reactions: List[Reaction]
  ) extends Bot {
    override var context: Context = Context()
    override def receiveUpdate(update: Update): Unit = {
      println("Update received")
      println(update)
    }
  }
}
