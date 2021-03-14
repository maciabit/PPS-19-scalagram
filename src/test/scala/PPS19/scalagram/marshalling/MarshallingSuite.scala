package PPS19.scalagram.marshalling

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MarshallingSuite extends AnyFunSuite {

  test("An empty string is always the same if converted to camelcase or snakecase") {
    val s = ""
    val c = CaseString(s)
    assert(s == c.camelCase && s == c.snakeCase)
  }

  test("A snakecase string can be converted to camelcase") {
    val s = "snake_case"
    val c = CaseString(s)
    assert(c.camelCase == "snakeCase")
  }

  test("A camelcase string can be converted to snakecase") {
    val c = "camelCase"
    val s = CaseString(c)
    assert(s.snakeCase == "camel_case")
  }
}
