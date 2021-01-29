package PPS19.scalagram.methods

import scala.util.{Try,Success,Failure}
import PPS19.scalagram.models.{HttpMethod, Update}
import io.circe.{HCursor, Json}
import requests.Response
import io.circe.parser._

import scala.util.Try

case class GetNewUpdates() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.GET, "getUpdates")
  def getNewUpdates(offset: Option[Int] = None,
                    limit: Option[Int] = None,
                    timeout: Option[Int] = None,
                    allowedUpdates: Option[Array[String]] = None): Try[List[Update]] = {
    val urlParams: Map[String, Any] = Map(
      "offset" -> offset,
      "limit" -> limit,
      "timeout" -> timeout,
      "allowed_Updates" -> allowedUpdates,
    ) filter (_._2.isDefined) map { case (key, value) => (key, value.get) }
    val res = method(urlParams)
    val parsed = parse(res.text()).getOrElse(Json.Null)
    val updates = decode[List[Update]](parsed.findAllByKey("ok").head.toString())
    updates match {
      case Left(error) => Failure(error)
      case Right(message) =>
        val json = parsed.findAllByKey("result").head.asArray.get
          .map(update => decode[Update](update.toString()))
          .map { case Right(update) => update }
          .toList
        Success(json)
    }
  }
}
