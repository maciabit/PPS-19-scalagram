package PPS19.scalagram.dsl

import PPS19.scalagram.logic.{Bot, Context, Middleware}
import PPS19.scalagram.models.BotToken
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TelegramBotDSLSuite extends AnyFunSuite {

  test("A bot token created with the DSL equals one created without") {
    object TestDSL extends TelegramBotDSL {
      token("<TOKEN>")
    }
    assert(TestDSL._token == BotToken("<TOKEN>"))
  }

  test("A bot created with the DSL equals one created without") {
    object TestDSL extends TelegramBotDSL {
      token("<TOKEN>")
    }
    TestDSL.build()
    assert(TestDSL._bot == Bot(BotToken("<TOKEN>")))
  }

}
