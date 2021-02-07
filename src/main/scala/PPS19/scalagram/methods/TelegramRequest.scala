package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.models.TelegramError
import io.circe.Json
import io.circe.parser.{decode, parse}
import requests.{MultiItem, Requester}

import java.io.File
import scala.util.{Failure, Success, Try}

trait TelegramRequest[T] {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  val token: BotToken

  val request: Requester

  val endpoint: String

  val urlParams: Map[String, Any]

  val multipartFormData: Map[String, String] = Map.empty

  def parseSuccessResponse(json: Json): Try[T]

  def call(): Try[T] = {
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
    val url = s"$TELEGRAM_API_URL${token.token}/$endpoint?$query"
    val req = multiItem match {
      case Nil => Try(request(url))
      case _ => Try(request(url, data = requests.MultiPart(multiItem:_*)))
    }
    req match {
      case Success(response) =>
        val json = parse(response.text()).getOrElse(Json.Null)
        json.findAllByKey("ok").head.toString() match {
          case "false" => Failure(decode[TelegramError](json.toString()).getOrElse(null))
          case "true" => parseSuccessResponse(json.findAllByKey("result").head)
        }
      case Failure(e) => Failure(e)
    }
  }
}
