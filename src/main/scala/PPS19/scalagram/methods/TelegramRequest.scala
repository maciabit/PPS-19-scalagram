package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.models.TelegramError
import PPS19.scalagram.utils.Props
import io.circe.Json
import io.circe.parser.{decode, parse}
import requests.{MultiItem, Requester}

import java.io.File
import scala.util.{Failure, Success, Try}

trait TelegramRequest[T] {

  val request: Requester

  val endpoint: String

  val urlParams: Map[String, Any]

  val multipartFormData: Map[String, String] = Map.empty

  def perform(): Try[Json] = {
    val query = urlParams
      .filter {
        case (_, None) => false
        case _ => true
      }
      .map {
        case (key, Some(value)) => (key, value)
        case (key, value) => (key, value)
      }
      .toUrlQuery
    val multiItem: List[MultiItem] = multipartFormData
      .map {
        case (key, value) =>
          val file = new File(value)
          requests.MultiItem(key, file, file.getName)
      }
      .toList
    val url = s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?$query"
    val req = multiItem match {
      case Nil => Try(request(url))
      case _ => Try(request(url, data = requests.MultiPart(multiItem:_*)))
    }
    req match {
      case Success(response) =>
        val parsed = parse(response.text()).getOrElse(Json.Null)
        parsed.findAllByKey("ok").head.toString() match {
          case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
          case "true" => Success(parsed.findAllByKey("result").head)
        }
      case Failure(e) => Failure(e)
    }
  }

  def call(): Try[T]
}
