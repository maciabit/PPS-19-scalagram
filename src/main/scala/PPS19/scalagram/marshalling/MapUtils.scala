package PPS19.scalagram.marshalling

import io.circe.{Json, JsonObject}
import shapeless.HList.ListCompat.::

import scala.::
import scala.util.control.TailCalls.{TailRec, done, tailcall}

object MapUtils {
  implicit class MapToUrlParams(map: Map[String, Any]) {
    def toUrlQuery: String = (map map {
      case (key, value) => s"${CaseString(key).snakeCase}=${value.toString}"
    }).mkString("&")
  }
}


