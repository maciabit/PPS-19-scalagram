package PPS19.scalagram.methods

import scala.util.{Failure, Success, Try}
import PPS19.scalagram.models.{TelegramError, Update}
import io.circe.Json
import requests.Response
import io.circe.parser._

case class GetNewUpdates() {
  val method: Map[String, Any] => Try[Response] = TelegramRequest.telegramApiRequest(requests.get, "getUpdates")
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
    if(res.isSuccess) {
      val parsed = parse(res.get.text()).getOrElse(Json.Null)
      parsed.findAllByKey("ok").head.toString() match {
        case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
        case "true" =>
          val json = parsed.findAllByKey("result").head.asArray.get
            .map(update => decode[Update](update.toString()))
            .map { case Right(update) => update }
            .toList
          Success(json)
      }
    } else {
      Failure(TelegramError.connectionError)
    }
  }
}
