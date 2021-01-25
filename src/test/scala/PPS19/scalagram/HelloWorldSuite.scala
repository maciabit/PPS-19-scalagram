/*
 * This Scala Testsuite was generated by the Gradle 'init' task.
 */
package PPS19.scalagram

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

@RunWith(classOf[JUnitRunner])
class HelloWorldSuite extends AnyFunSuite {

  test("someLibraryMethod is always true") {
    def library = new HelloWorld()
    assert(library.someMethod())
  }

  test("Circe can perform a basic conversion") {
    sealed trait Foo
    case class Bar(xs: Vector[String]) extends Foo
    case class Qux(i: Int, d: Option[Double]) extends Foo

    val foo: Foo = Qux(13, Some(14.0))

    val json = foo.asJson.noSpaces
    assert(json == "{\"Qux\":{\"i\":13,\"d\":14.0}}")

    val decodedFoo = decode[Foo](json)
    assert(decodedFoo.toString == "Right(Qux(13,Some(14.0)))")
  }
}
