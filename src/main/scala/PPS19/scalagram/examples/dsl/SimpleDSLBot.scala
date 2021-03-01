package PPS19.scalagram.examples.dsl

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

  token(
    Props.get("token")
  )

  mode(
    POLLING interval 300.milliseconds timeoutDelay 1.days
  )

  middlewares(
    <> { context =>
      println(context.update.get)
      true
    }

    <> { _ =>
      println("Second middleware")
      true
    }
  )

  reactions(
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

    <* ("Message1" | "Message2" | "Message3")
    >> "MessageN edited"

    <# "boh"
    >> "Regex"

    << "/rk"
    >> "Reply keyboard" - Keyboard(
      "Button 1",
      "Button 2" :: "Button 3"
    )

    << "/ik"
    >> "Inline keyboard" - InlineKeyboard(
      Callback("Button 1" -> "callback"),
      "Button 2" :: "Button 3"
    )

    << "/html"
    >> HTML("Keyboard with <b>HTML</b>") - InlineKeyboard("A" :: "B" :: "C")

    << "/md"
    >> MarkdownV2("Keyboard with *Markdown*") - InlineKeyboard("A" :: "B" :: "C")

    << "/scene1"
    >> { context =>
      context.reply("You are now inside a scene")
      println("You are now inside a scene")
      context.enterScene("FIRST_SCENE")
      println(context.activeScene)
    }

    << "/scene2"
    >> { context =>
      context.reply("You are now inside a scene\nType /back to exit")
      println("You are now inside a scene")
      context.enterScene("SECOND_SCENE")
      println(context.activeScene)
    }

    << "/back"
    >> { context =>
      context.leaveScene()
      context.reply("Ready")
    }

    /*<@ ()
    >> "Help"

    << ()
    >> "What?"

    <* ()
    >> "Any message edited"

    <^ ()
    >> "Message pinned"

    <+ ()
    >> "Welcome"

    </ ()
    >> "Goodbye"*/
  )

  scenes(

    scene(
      "FIRST_SCENE"

      <| "FIRST_STEP"
      >> { context =>
        context.reply("First scene step")
        println("First scene step")
        context.nextStep()
      }

      <| "SECOND_STEP"
      >> { context =>
        context.reply("Second scene step")
        println("Second scene step")
        context.nextStep()
      }

      <| "THIRD_STEP"
      >> { context =>
        context.reply("Third scene step")
        println("Third scene step")
        context.leaveScene()
      }
    )

    scene(
      "SECOND_SCENE"
      <| "ONLY_STEP"
      >> { context =>
        context.reply("This scene has only one step")
        println("Scene step")
      }
    )
  )

}

object SimpleDSLBotMain extends App {
  SimpleDSLBot.start()
  println("Bot started")
}
