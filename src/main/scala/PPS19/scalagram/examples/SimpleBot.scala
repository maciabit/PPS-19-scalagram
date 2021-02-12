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
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Hello, world!")
        println("Hello, world!")
      case _ =>
    }
  }

  val keyboard = Bot.onMessage("/keyboard") { context =>
    context.chat match {
      case Some(chatId) =>
        val k = Some(
          InlineKeyboardMarkup(
            List(
              List(
                InlineKeyboardButton("Button", callbackData = Some("callback"))
              )
            )
          )
        )
        bot.sendMessage(chatId, "Here's a keyboard!", replyMarkup = k)
      case _ =>
    }
  }

  val onCallback = Bot.onCallbackQuery("callback") { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Thanks for clicking the button")
      case _ =>
    }
  }

  val onPinned = Bot.onMessagePinned { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Woa, someone pinned a message \uD83D\uDE32")
      case _ =>
    }
  }

  val onMessageEdited = Bot.onMessageEdited() { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Do you have something to hide?")
      case _ =>
    }
  }

  val onChatEnter = Bot.onChatEnter { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Welcome!")
      case _ =>
    }
  }

  val onChatLeave = Bot.onChatLeave { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Goodbye")
      case _ =>
    }
  }

  val enterScene = Bot.onMessage("/scene") { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "You are now inside a scene")
        println("You are now inside a scene")
        context.enterScene("TEST_SCENE")
      case _ =>
    }
  }

  val scene = Scene(
    "TEST_SCENE",
    List(
      Step(
        "FIRST_STEP",
        { context =>
          context.chat match {
            case Some(chatId) =>
              bot.sendMessage(chatId, "First scene step")
              println("First scene step")
              context.nextStep()
            case _ =>
          }
        }
      ),
      Step(
        "SECOND_STEP",
        { context =>
          context.chat match {
            case Some(chatId) =>
              bot.sendMessage(chatId, "Second scene step")
              println("Second scene step")
              context.nextStep()
            case _ =>
          }
        }
      ),
      Step(
        "THIRD_STEP",
        { context =>
          context.chat match {
            case Some(chatId) =>
              bot.sendMessage(chatId, "Third scene step")
              println("Third scene step")
              context.leaveScene()
            case _ =>
          }
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
}
