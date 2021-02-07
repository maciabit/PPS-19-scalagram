package PPS19.scalagram.methods

import scala.util.{Success, Try}
import PPS19.scalagram.models.Update
import io.circe.Json
import io.circe.parser._
import requests.Requester

case class GetNewUpdates(
  offset: Option[Int] = None,
  limit: Option[Int] = None,
  timeout: Option[Int] = None,
  allowedUpdates: Option[Array[String]] = None
) extends TelegramRequest[List[Update]] {

  val request: Requester = requests.get

  val endpoint: String = "getUpdates"

  val urlParams: Map[String, Any] = Map(
    "offset" -> offset,
    "limit" -> limit,
    "timeout" -> timeout,
    "allowed_Updates" -> allowedUpdates,
  )

  def parseSuccessResponse(json: Json): Try[List[Update]] = {
    val updates = json.asArray.get
      .map { update => decode[Update](update.toString()) }
      .collect { case Right(update) => update }
      .toList
    Success(updates)
  }
}