package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.item.keyboard.{InlineKeyboard, Keyboard}
import PPS19.scalagram.dsl.mode.WorkingMode._
import reactions.action.Action.ActionConversions._
import PPS19.scalagram.dsl.item.keyboard.Utils._
import PPS19.scalagram.dsl.reactions.action.Action.{HTML, MarkdownV2}
import PPS19.scalagram.dsl.reactions.trigger.TriggerList
import PPS19.scalagram.utils.Props

import scala.concurrent.duration.DurationInt

object SimpleBot extends DSL {

  token (
    Props.get("token")
  )

  mode (
    POLLING interval 300.milliseconds timeoutDelay 1.days
  )

  // with curly brackets the newline syntax is not enabled (wtf)
  middlewares (
    <-> { _ =>
      println("First middleware")
      true
    }

    <-> { _ =>
      println("Second middleware")
      true
    }
  )

  // with curly brackets the newline syntax is not enabled (wtf)
  // we should try to remove the parenthesis to the first argument
  reactions (
    TriggerList(Nil)

    << "/uno"
    >> "uno"

    << "/due"
    >> "due"

    << "/rk"
    >> "Reply keyboard" - Keyboard(
      "Button 1",
      "Button 2" :: "Button 3"
    )

    << "/ik"
    >> "Inline keyboard" - InlineKeyboard("A" :: "B" :: "C")

    << "/html"
    >> "Keyboard with <b>HTML</b>" - HTML - InlineKeyboard("A" :: "B" :: "C")

    << "/md"
    >> "Keyboard with *Markdown*" - MarkdownV2 - InlineKeyboard("A" :: "B" :: "C")

    << "/shutdown"
    >> {_ => System.exit(0)}
  )

}

object TestDSL extends App {
  SimpleBot.start()
}
