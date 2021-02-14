package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.bot.ComposableBot
import PPS19.scalagram.dsl.bot.WorkingMode._
import PPS19.scalagram.dsl.reactions.Conversions.sendMessageConversion

import scala.concurrent.duration.DurationInt

object SimpleBot extends ComposableBot {

  token {
    "1502535921:AAEmuIayUnTQ0ExsN3R95SyOGYaaX6vgzJs"
  }

  mode {
    POLLING interval 300.milliseconds timeoutDelay 1.days debug false
  }

  reactions (
    << ("/uno")
    >> "uno"

    << "/due"
    >> "due"

    << "/shutdown"
    >> {_ => System.exit(0)}
  )

  middlewares {
    <-> { c => {
      c.bot.sendMessage(c.chat.get,  "453")
      true
    } }
  }

}

object TestDSL extends App {
  SimpleBot.start()
}
