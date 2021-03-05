package PPS19.scalagram.logic

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BotSuite extends AnyFunSuite {

  val bot: Bot = Bot(BotToken(""), List(), List(), List())
  val context: Context = Context(bot)

  test("A Middleware can return true") {
    val middleware = Middleware(_ => true)
    assert(middleware.operation(context))
  }

  test("A Middleware can return false") {
    val middleware = Middleware(_ => false)
    assert(!middleware.operation(context))
  }

  test("A Reaction always returns false") {
    val trigger = Trigger(_ => true)
    val reaction = Reaction(trigger, _ => {})
    assert(!reaction.operation(context))
  }

  test("A Step always returns false") {
    val step = Step("name", _ => {})
    assert(!step.operation(context))
  }

  test("Bot creation and extractor method are working as intended") {
    val token = BotToken("<TOKEN>")
    val middlewares = List(Middleware(_ => true))
    val scenes = List(Scene("<SCENE_NAME>", List(Step("<ACTION_NAME>", _ => {}))))
    val reactions = List(Reaction(Trigger(_ => true), _ => {}))
    val bot = Bot(token, middlewares, reactions, scenes)
    assert(bot match {
      case Bot(t, m, r, s) if t == token && m == middlewares && r == reactions && s == scenes => true
      case _                                                                                  => false
    })
  }
}
