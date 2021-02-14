package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.bot.ComposableBot
import PPS19.scalagram.dsl.bot.WorkingMode._

import scala.concurrent.duration.DurationInt
import PPS19.scalagram.dsl.reactions.Conversions.sendMessageConversion

object SimpleBot extends ComposableBot {

  token {
    "1502535921:AAEmuIayUnTQ0ExsN3R95SyOGYaaX6vgzJs"
  }

  mode {
    POLLING interval 300.milliseconds timeoutDelay 1.days debug false
  }

  // with curly brackets the newline syntax is not enabled (wtf)
  reactions (
    << ("/uno")
    >> "uno"

    << "/due"
    >> "due"

    << "/shutdown"
    >> {_ => System.exit(0)}
  )

  // with curly brackets the newline syntax is not enabled (wtf)
  middlewares (
    <-> { c => {
      c.bot.sendMessage(c.chat.get,  "Ciao")
      true
    } }

    <-> { c => {
      c.bot.sendMessage(c.chat.get,  "Filippo")
      true
    } }
  )

}

object TestDSL extends App {
  SimpleBot.start()
}
