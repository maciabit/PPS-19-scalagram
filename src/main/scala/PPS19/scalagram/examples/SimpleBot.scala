package PPS19.scalagram.examples

import PPS19.scalagram.logic.scenes.{Scene, Step}
import PPS19.scalagram.logic.{Scalagram, Middleware}
import PPS19.scalagram.models.{BotToken, InlineKeyboardButton, InlineKeyboardMarkup}
import PPS19.scalagram.modes.polling.Polling
import PPS19.scalagram.utils.Props

import scala.concurrent.duration.DurationInt

private[examples] object SimpleBot extends App {
  val botToken = BotToken(Props.get("token"))

  val middlewares = List(
    Middleware { _ =>
      println("Update received")
      true
    }
  )

  val reactions = List(
    Scalagram.onStart { context =>
      context.reply("Thanks for starting me")
      println("Start")
    },
    Scalagram.onHelp { context =>
      context.reply("Here is some useful info")
      println("Help")
    },
    Scalagram.onMessage("/ciao", "Ciao") { context =>
      context.reply("Hello, world!")
      println("Hello, world!")
    },
    Scalagram.onMessage("/keyboard") { context =>
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
    Scalagram.onCallbackQuery("callback") { context =>
      println("callback")
      context.reply("Thanks for clicking the button")
    },
    Scalagram.onMessagePinned { context =>
      context.reply("Woa, someone pinned a message \uD83D\uDE32")
    },
    Scalagram.onMessageEdited() { context =>
      context.reply("Do you have something to hide?")
    },
    Scalagram.onChatEnter { context =>
      context.reply("Welcome!")
    },
    Scalagram.onChatLeave { context =>
      context.reply("Goodbye")
    },
    Scalagram.onMessage("/scene") { context =>
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

  val bot = Scalagram(botToken, middlewares, reactions, scenes)
  bot.launch(Polling(300.milliseconds))
  println("Bot started")
}
