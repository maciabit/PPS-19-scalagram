package PPS19.scalagram.examples

import PPS19.scalagram.logic.{Bot, BotToken, Middleware, Scene, Step}
import PPS19.scalagram.models.{InlineKeyboardButton, InlineKeyboardMarkup}
import PPS19.scalagram.modes.polling.Polling
import PPS19.scalagram.utils.Props

import scala.concurrent.duration.DurationInt

object SimpleBot extends App {
  var bot = Bot(BotToken(Props.get("token")))

  val middlewares = List(
    Middleware { _ =>
      println("Update received")
      true
    }
  )

  val hello = Bot.onMessage("/ciao", "Ciao") { context =>
    context.reply("Hello, world!")
    println("Hello, world!")
  }

  val keyboard = Bot.onMessage("/keyboard") { context =>
    val k = Some(
      InlineKeyboardMarkup(
        List(
          List(
            InlineKeyboardButton.callback("Button", "callback")
          )
        )
      )
    )
    context.reply("Here's a keyboard!", replyMarkup = k)
  }

  val onCallback = Bot.onCallbackQuery("callback") { context =>
    context.reply("Thanks for clicking the button")
  }

  val onPinned = Bot.onMessagePinned { context =>
    context.reply("Woa, someone pinned a message \uD83D\uDE32")
  }

  val onMessageEdited = Bot.onMessageEdited() { context =>
    context.reply("Do you have something to hide?")
  }

  val onChatEnter = Bot.onChatEnter { context =>
    context.reply("Welcome!")
  }

  val onChatLeave = Bot.onChatLeave { context =>
    context.reply("Goodbye")
  }

  val enterScene = Bot.onMessage("/scene") { context =>
    context.reply("You are now inside a scene")
    println("You are now inside a scene")
    context.enterScene("TEST_SCENE")
  }

  val scene = Scene(
    "TEST_SCENE",
    List(
      Step(
        "FIRST_STEP",
        { context =>
          context.reply("First scene step")
          println("First scene step")
          context.nextStep()
        }
      ),
      Step(
        "SECOND_STEP",
        { context =>
          context.reply("Second scene step")
          println("Second scene step")
          context.nextStep()
        }
      ),
      Step(
        "THIRD_STEP",
        { context =>
          context.reply("Third scene step")
          println("Third scene step")
          context.leaveScene()
        }
      )
    )
  )

  val reactions = List(
    hello,
    keyboard,
    onCallback,
    onPinned,
    onMessageEdited,
    onChatEnter,
    onChatLeave,
    enterScene
  )

  bot = Bot(BotToken(Props.get("token")), middlewares, reactions, List(scene))
  bot.launch(Polling(5.seconds))
  println("Bot started")
}
