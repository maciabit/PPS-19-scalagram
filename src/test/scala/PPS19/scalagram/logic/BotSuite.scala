package PPS19.scalagram.logic

import PPS19.scalagram.models.Update
import io.circe.jawn.decode
import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BotSuite extends AnyFunSuite with BeforeAndAfter {

  var context: Context = _

  before {
    val bot = Bot(BotToken(""), List(), List(), List())
    val message = """{
      "update_id": 971212771,
      "channel_post": {
        "message_id": 344544,
        "author_signature": "ProphecyOfferte",
        "chat": {
          "id": -1001364807173,
          "title": "Bot Log",
          "type": "channel"
        },
        "date": 1611486001,
        "text": "Alive at 2021-01-24 12:00:00"
      }
    }"""
    val update = decode[Update](message)
    context = Context(bot, update.getOrElse(null))
  }

  test("A Middleware can return true") {
    val middleware = Middleware(_ => true)
    assert(middleware.operation(context))
  }

  test("A Middleware can return false") {
    val middleware = Middleware(_ => false)
    assert(!middleware.operation(context))
  }

  test("A reaction always returns false") {
    val trigger = Trigger(_ => true)
    val reaction = Reaction(trigger, _ => {})
    assert(!reaction.operation(context))
  }

  test("A Bot can be created") {
    val token = BotToken("<TOKEN>")
    val middlewares = List(Middleware(_ => true))
    val scenes = List(Scene(Reaction(Trigger(_ => true), _ => {})))
    val reactions = List(Reaction(Trigger(_ => true), _ => {}))
    val bot = Bot(token, middlewares, scenes, reactions)
    assert(bot.token == token)
    assert(bot.middlewares == middlewares)
    assert(bot.scenes == scenes)
    assert(bot.reactions == reactions)
  }

  test("A reaction can be created with the onCommand method") {
    val bot = Bot(BotToken(""), List(), List(), List())
    bot.onCommand("/hello") { _ =>
      println("ciao")
    }
  }
}
