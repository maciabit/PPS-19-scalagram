package PPS19.scalagram.examples

import PPS19.scalagram.dsl.TelegramBotDSL
import PPS19.scalagram.dsl.keyboard.KeyboardButtonContainer.Callback
import PPS19.scalagram.dsl.keyboard.KeyboardConversions._
import PPS19.scalagram.dsl.keyboard.KeyboardUtils._
import PPS19.scalagram.dsl.mode.WorkingMode._
import PPS19.scalagram.dsl.reactions.ReactionConversions._
import PPS19.scalagram.dsl.reactions.ReactionUtils.{StringExtension, _}
import PPS19.scalagram.utils.Props

import scala.concurrent.duration.DurationInt

object SimpleDSLBot extends TelegramBotDSL {

  token (
    Props.get("token")
  )

  mode (
    POLLING interval 300.milliseconds timeoutDelay 1.days
  )

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

  reactions (

    !!
    >> "Hello"

    << ("/uno" | "uno" | "no")
    >> "uno"

    << "/due"
    >> { context =>
      context.reply("due")
    }

    <~ "callback"
    >> "Callback"

    <* "Message"
    >> "Message edited"

    <* ("Message" | "Message2" | "Message3")
    >> "Message edited"

    <# "boh"
    >> "Regex"

    << "/rk"
    >> "Reply keyboard" - Keyboard(
      "Button 1",
      "Button 2" :: "Button 3"
    )

    << "/ik"
    >> "Inline keyboard" - InlineKeyboard(
      Callback("Button 1" -> "data"),
      "Button 2" :: "Button 3"
    )

    << "/html"
    >> HTML("Keyboard with <b>HTML</b>") - InlineKeyboard("A" :: "B" :: "C")

    << "/md"
    >> MarkdownV2("Keyboard with *Markdown*") - InlineKeyboard("A" :: "B" :: "C")
  )

  /*scenes (

    scene("").steps (
      step("")
      >>("")
    )

    scene("").steps (
      step("")
        >>("")
    )
  )*/

}

object SimpleDSLBotMain extends App {
  SimpleDSLBot.start()
  println("Bot started")
}
