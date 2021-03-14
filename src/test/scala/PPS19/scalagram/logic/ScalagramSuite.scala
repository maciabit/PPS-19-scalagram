package PPS19.scalagram.logic

import PPS19.scalagram.logic.scenes.{Scene, Step}
import PPS19.scalagram.models.payloads.TextMessage
import PPS19.scalagram.models.updates.{MessageReceived, UnknownUpdate}
import PPS19.scalagram.models.{BotToken, UnknownChat}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScalagramSuite extends AnyFunSuite {

  val bot: Scalagram = Scalagram(BotToken(""), List(), List(), List())
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

  test("A Step returns false if not supplied with a MessageUpdate") {
    var res = false
    val step = Step("name", _ => res = true)
    val someOtherUpdate = UnknownUpdate(0)
    context.update = someOtherUpdate
    assert(step.operation(context) && !res)
  }

  test("A step gets executed if supplied with a MessageUpdate") {
    var res = false
    val step = Step("name", _ => res = true)
    val messageUpdate = MessageReceived(0, TextMessage(0, UnknownChat, 0, "Message"))
    context.update = messageUpdate
    assert(!step.operation(context) && res)
  }

  test("Bot creation and extractor method are working as intended") {
    val token = BotToken("<TOKEN>")
    val middlewares = List(Middleware(_ => true))
    val scenes = List(Scene("<SCENE_NAME>", List(Step("<ACTION_NAME>", _ => {}))))
    val reactions = List(Reaction(Trigger(_ => true), _ => {}))
    val bot = Scalagram(token, middlewares, reactions, scenes)
    assert(bot match {
      case Scalagram(t, m, r, s) if t == token && m == middlewares && r == reactions && s == scenes => true
      case _                                                                                        => false
    })
  }
}
