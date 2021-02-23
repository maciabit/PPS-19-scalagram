package PPS19.scalagram.utils

import PPS19.scalagram.logic.{Bot, BotToken}

import scala.util.{Failure, Success}

object ResetBotUpdates extends App {
  private val bot = Bot(BotToken(Props.get("token")))
  bot.getUpdates(Some(-1)) match {
    case Failure(_) => println("Reset updates failed")
    case Success(updates) if updates.nonEmpty =>
      val updateId = updates.last.updateId
      val res = bot.getUpdates(Some(updateId + 1))
      if (res.isSuccess) {
        println("Successfully confirmed all pending updates")
      } else {
        println("Reset updates failed")
      }
    case _ => println("Successfully confirmed all pending updates")
  }
}
