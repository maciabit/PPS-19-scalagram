/*
 * This Scala source file was generated by the Gradle 'init' task.
 */
package PPS19.scalagram

import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

class HelloWorld {
  def someMethod(): Boolean = true
}

object HelloWorld extends App {
  println("Hello, world!")

  val r = requests.get("https://api.github.com/users/lihaoyi")

  println(r.statusCode)
  println(r.headers("content-type"))
  println(r.text())

  sealed trait Foo
  case class Bar(xs: Vector[String]) extends Foo
  case class Qux(i: Int, d: Option[Double]) extends Foo

  val foo: Foo = Qux(13, Some(14.0))

  val json = foo.asJson.noSpaces
  println(json)

  val decodedFoo = decode[Foo](json)
  println(decodedFoo)
}
