package PPS19.scalagram.logic

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BotSuite extends AnyFunSuite {

  test("A Middleware can return true") {
    val middleware = Middleware(() => true)
    assert(middleware.execute())
  }

  test("A Middleware can return false") {
    val middleware = Middleware(() => false)
    assert(!middleware.execute())
  }

  test("A reaction always returns false") {
    val reaction = Reaction(() => {})
    assert(!reaction.execute())
  }

  test("A Bot can be created") {
    val token = BotToken("<TOKEN>")
    val middlewares = List(Middleware(() => true))
    val scenes = List(Scene(Reaction(() => {})))
    val reactions = List(Reaction(() => {}))
    val bot = Bot(token, middlewares, scenes, reactions)
    assert(bot.token == token)
    assert(bot.middlewares == middlewares)
    assert(bot.scenes == scenes)
    assert(bot.reactions == reactions)
  }
}
