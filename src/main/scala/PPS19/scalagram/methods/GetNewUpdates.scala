package PPS19.scalagram.methods

import scala.util.{Failure, Success, Try}
import PPS19.scalagram.models.Update
import io.circe.Json
import io.circe.parser._

case class GetNewUpdates() {

  private val method: Map[String, Any] => Try[Json] = telegramApiRequest(requests.get, "getUpdates")

  def call(
    offset: Option[Int] = None,
    limit: Option[Int] = None,
    timeout: Option[Int] = None,
    allowedUpdates: Option[Array[String]] = None
  ): Try[List[Update]] = {
    val urlParams: Map[String, Any] = Map(
      "offset" -> offset,
      "limit" -> limit,
      "timeout" -> timeout,
      "allowed_Updates" -> allowedUpdates,
    )
    val res = method(urlParams)
    res match {
      case Success(json) =>
        val updates = json.asArray.get
          .map { update => decode[Update](update.toString()) }
          .collect { case Right(update) => update }
          .toList
        Success(updates)
      case Failure(error) => Failure(error)
    }
  }
}