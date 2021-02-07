package PPS19.scalagram.methods

import scala.util.{Failure, Success, Try}
import PPS19.scalagram.models.Update
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

  def call(): Try[List[Update]] = perform() match {
    case Success(json) =>
      val updates = json.asArray.get
        .map { update => decode[Update](update.toString()) }
        .collect { case Right(update) => update }
        .toList
      Success(updates)
    case Failure(error) => Failure(error)
  }
}