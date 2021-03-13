package PPS19.scalagram.dsl.middleware

import PPS19.scalagram.dsl._
import PPS19.scalagram.logic.{Context, Middleware}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MiddlewareDSLSuite extends AnyFunSuite {

  test("A middleware list created with the DSL equals a middleware list created without it") {
    val f1: Context => Boolean = _ => true
    val f2: Context => Boolean = _ => false
    object TestDSL extends TelegramBotDSL {
      middlewares(<>(f1).<>(f2))
    }
    val middlewares = List(Middleware(f1), Middleware(f2))
    assert(TestDSL._middlewares == middlewares)
  }

}
