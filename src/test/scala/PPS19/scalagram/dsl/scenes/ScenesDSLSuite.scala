package PPS19.scalagram.dsl.scenes

import PPS19.scalagram.dsl._
import PPS19.scalagram.logic.{Context, Middleware, Scene, Step}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ScenesDSLSuite extends AnyFunSuite {

  test("A scene list created with the DSL equals one created without it") {
    val f1: Context => Unit = _ => {}
    val f2: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      scenes(
        scene("1".<|("S1").>>(f1).<|("S2").>>(f2)).scene("2".<|("S1").>>(f1))
      )
    }
    val scenes = List(
      Scene("1", List(Step("S1", f1), Step("S2", f2))),
      Scene("2", List(Step("S1", f1)))
    )
    assert(TestDSL._scenes == scenes)
  }

}
