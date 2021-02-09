package PPS19.scalagram.marshalling

object MapUtils {
  implicit class MapToUrlParams(map: Map[String, Any]) {
    def toUrlQuery: String =
      (map map {
        case (key, value) => s"${CaseString(key).snakeCase}=${value.toString}"
      }).mkString("&")
  }
}
