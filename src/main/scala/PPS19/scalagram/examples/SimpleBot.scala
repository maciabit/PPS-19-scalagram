package PPS19.scalagram.examples

import PPS19.scalagram.logic.{Bot, BotToken, Middleware}
import PPS19.scalagram.modes.Polling
import PPS19.scalagram.utils.Props

import scala.concurrent.duration.DurationInt

object SimpleBot extends App {

  val middlewares = List(
    Middleware { context =>
      context.log("Update received")
      true
    }
  )

  val command = Bot.onCommand("/ciao") { context =>
    context.log("Hello, world!")
  }

  val bot = Bot(BotToken(Props.get("token")), middlewares, List(command))
  bot.launch(Polling(5.seconds))
}
