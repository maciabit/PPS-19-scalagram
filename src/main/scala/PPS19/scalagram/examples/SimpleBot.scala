package PPS19.scalagram.examples

import PPS19.scalagram.logic.{Bot, BotToken, Middleware, Scene, Step}
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

  val hello = Bot.onCommand("/ciao") { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "Hello, world!")
        println("Hello, world!")
      case _ =>
    }
  }

  val enterScene = Bot.onCommand("/scene") { context =>
    context.chat match {
      case Some(chatId) =>
        bot.sendMessage(chatId, "You are now inside a scene")
        println("You are now inside a scene")
        context.enterScene("TEST_SCENE")
      case _ =>
    }
  }

  val scene = Scene("TEST_SCENE", List(
    Step("FIRST_STEP", { context =>
      context.chat match {
        case Some(chatId) =>
          bot.sendMessage(chatId, "First scene step")
          println("First scene step")
          context.nextStep()
        case _ =>
      }
    }),
    Step("SECOND_STEP", { context =>
      context.chat match {
        case Some(chatId) =>
          bot.sendMessage(chatId, "Second scene step")
          println("Second scene step")
          context.nextStep()
        case _ =>
      }
    }),
    Step("THIRD_STEP", { context =>
      context.chat match {
        case Some(chatId) =>
          bot.sendMessage(chatId, "Third scene step")
          println("Third scene step")
          context.leaveScene()
        case _ =>
      }
    })
  ))

  bot = Bot(BotToken(Props.get("token")), middlewares, List(hello, enterScene), List(scene))
  bot.launch(Polling(5.seconds))
}
