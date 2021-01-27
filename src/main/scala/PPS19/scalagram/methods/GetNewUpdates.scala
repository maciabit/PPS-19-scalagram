package PPS19.scalagram.methods
import PPS19.scalagram.models.Update
import io.circe.{HCursor, Json}
import requests.Response
import io.circe.parser._

case class GetNewUpdates() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.GET, "getUpdates")
  def getNewUpdates(offset: Option[Int] = None, offsetLimit: Option[Int] = None, date: Option[Int] = None, qts: Option[Int] = None): List[Update] = {
    val urlParams: Map[String, Int] = Map(
      "pts" -> offset,
      "pts_total_limit" -> offsetLimit,
      "date" -> date,
      "qts" -> qts,
    ) filter { case (_, value) => value.isDefined } map { case (key, value) => (key, value.get) }
    val res = method(urlParams)
    if (res.statusCode != 200)
      return List.empty
    val json = parse(res.text()).getOrElse(Json.Null).findAllByKey("result").head.asArray.get
      .map(update => decode[Update](update.toString()))
      .map { case Right(update) => update }
      .toList
    json
  }
}





