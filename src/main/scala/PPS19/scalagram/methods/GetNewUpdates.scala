package PPS19.scalagram.methods
import PPS19.scalagram.models.{HttpMethod, Update}
import io.circe.{HCursor, Json}
import requests.Response
import io.circe.parser._

case class GetNewUpdates() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.GET, "getUpdates")
  def getNewUpdates(offset: Option[Int] = None,
                    limit: Option[Int] = None,
                    timeout: Option[Int] = None,
                    allowedUpdates: Option[Array[String]] = None): List[Update] = {
    val urlParams: Map[String, Any] = Map(
      "offset" -> offset,
      "limit" -> limit,
      "timeout" -> timeout,
      "allowed_Updates" -> allowedUpdates,
    ) filter (_._2.isDefined) map { case (key, value) => (key, value.get) }
    val res = method(urlParams)
    if (res.statusCode != 200) {
      Left(parse(res.text()).getOrElse(Json.Null))
    }
    val json = parse(res.text()).getOrElse(Json.Null).findAllByKey("result").head.asArray.get
      .map(update => decode[Update](update.toString()))
      .map { case Right(update) => update }
      .toList
    json
  }
}
