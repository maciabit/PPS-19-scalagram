package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.mode.WorkingMode._
import PPS19.scalagram.dsl.keyboard.Conversion._
import PPS19.scalagram.dsl.keyboard.{InlineKeyboard, Keyboard}
import PPS19.scalagram.dsl.keyboard.StringUtils._

import scala.concurrent.duration.DurationInt
import PPS19.scalagram.dsl.reactions.Action._

object SimpleBot extends DSL {

  token {
    "1502535921:AAEmuIayUnTQ0ExsN3R95SyOGYaaX6vgzJs"
  }

  mode {
    POLLING interval 300.milliseconds timeoutDelay 1.days debug false
  }

  // with curly brackets the newline syntax is not enabled (wtf)
  // we should try to remove the parenthesis to the first argument
  reactions (
    << ("/uno")
    >> "uno"

    << "/due"
    >> "due"

    << "/rk"
    >> Keyboard(
      "b1",
      "Button 2" :: "Button 3"
    )

    << "/ik"
    >> InlineKeyboard("A" :: "B" :: "C")

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
