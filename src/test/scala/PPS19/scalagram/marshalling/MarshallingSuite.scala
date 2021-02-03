package PPS19.scalagram.marshalling

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import PPS19.scalagram.marshalling.MapUtils._


@RunWith(classOf[JUnitRunner])
class MarshallingSuite extends AnyFunSuite {

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

  test("A map[key, value] can be converted to snakecase string") {
    val map = Map(
      "chatId" -> -10053564,
      "text" -> "lorem ipsium",
      "booleanField" -> true
    )
    assert(map.toUrlQuery == "chat_id=-10053564&text=lorem ipsium&boolean_field=true")
  }
}
