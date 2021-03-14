package PPS19.scalagram.dsl

<<<<<<< HEAD:src/test/scala/PPS19/scalagram/dsl/TelegramBotDSLSuite.scala
import PPS19.scalagram.logic.Bot
=======
import PPS19.scalagram.logic.Scalagram
>>>>>>> develop:src/test/scala/PPS19/scalagram/dsl/ScalagramDSLSuite.scala
import PPS19.scalagram.models.BotToken
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScalagramDSLSuite extends AnyFunSuite {

  test("A bot token created with the DSL equals one created without") {
    object TestDSL extends ScalagramDSL {
      token("<TOKEN>")
    }
    assert(TestDSL._token == BotToken("<TOKEN>"))
  }

  test("A bot created with the DSL equals one created without") {
    object TestDSL extends ScalagramDSL {
      token("<TOKEN>")
    }
    TestDSL.build()
    assert(TestDSL._bot == Scalagram(BotToken("<TOKEN>")))
  }

}
