package PPS19.scalagram.examples

import PPS19.scalagram.logic.{Bot, Middleware, Scene, Step}
import PPS19.scalagram.models.{BotToken, InlineKeyboardButton, InlineKeyboardMarkup}
import PPS19.scalagram.modes.polling.Polling
import PPS19.scalagram.utils.Props

import scala.concurrent.duration.DurationInt

object SimpleBot extends App {
  val botToken = BotToken(Props.get("token"))

  val middlewares = List(
    Middleware { _ =>
      println("Update received")
      true
    }
  )

  val reactions = List(
    Bot.onStart { context =>
      context.reply("Thanks for starting me")
      println("Start")
    },
    Bot.onHelp { context =>
      context.reply("Here is some useful info")
      println("Help")
    },
    Bot.onMessage("/ciao", "Ciao") { context =>
      context.reply("Hello, world!")
      println("Hello, world!")
    },
    Bot.onMessage("/keyboard") { context =>
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
    },
    Bot.onCallbackQuery("callback") { context =>
      println("callback")
      context.reply("Thanks for clicking the button")
    },
    Bot.onMessagePinned { context =>
      context.reply("Woa, someone pinned a message \uD83D\uDE32")
    },
    Bot.onMessageEdited() { context =>
      context.reply("Do you have something to hide?")
    },
    Bot.onChatEnter { context =>
      context.reply("Welcome!")
    },
    Bot.onChatLeave { context =>
      context.reply("Goodbye")
    },
    Bot.onMessage("/scene") { context =>
      context.reply("You are now inside a scene")
      println("You are now inside a scene")
      context.enterScene("TEST_SCENE")
    }
  )

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

  val scenes = List(scene)

  val bot = Bot(botToken, middlewares, reactions, scenes)
  bot.launch(Polling(300.milliseconds))
  println("Bot started")
}
