package PPS19.scalagram.dsl.mode

import PPS19.scalagram.dsl._
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

import scala.concurrent.duration.DurationInt

@RunWith(classOf[JUnitRunner])
class WorkingModeDSLSuite extends AnyFunSuite {

  test("A mode created with the DSL equals a mode created without it") {
    object TestDSL extends ScalagramDSL {
      mode(Polling interval 300.milliseconds timeoutDelay 1.days)
    }
    val mode = PPS19.scalagram.modes.polling.Polling(300.milliseconds, 1.days)
    assert(TestDSL._mode == mode)
  }

}
