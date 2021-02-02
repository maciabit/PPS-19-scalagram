package PPS19.scalagram.logic

import PPS19.scalagram.models.MessageUpdate
import PPS19.scalagram.models.messages.TextMessage
import PPS19.scalagram.modes.Mode

case class BotToken(token: String)

sealed trait Bot {
  val token: BotToken
  val middlewares: List[Middleware]
  val scenes: List[Scene]
  val reactions: List[Reaction]

  def onCommand(command: String)(action: Context => Unit): Reaction = {
    Reaction(
      Trigger {
        case MessageUpdate(_, message) if message.isInstanceOf[TextMessage] =>
          message.asInstanceOf[TextMessage].text == command
        case _ => false
      },
      action
    )
  }

  def launch(mode: Mode): Unit = mode.start(this)
}

object Bot {

  def apply(
    token: BotToken,
    middlewares: List[Middleware],
    scenes: List[Scene],
    reactions: List[Reaction]
  ): Bot = BotImpl(token, middlewares, scenes, reactions)

  def unapply(bot: Bot): Option[(BotToken, List[Middleware], List[Scene], List[Reaction])] =
    Some(bot.token, bot.middlewares, bot.scenes, bot.reactions)

  case class BotImpl (
    token: BotToken,
    middlewares: List[Middleware],
    scenes: List[Scene],
    reactions: List[Reaction]
  ) extends Bot
}
