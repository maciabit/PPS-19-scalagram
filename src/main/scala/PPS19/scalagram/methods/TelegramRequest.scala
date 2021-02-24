package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.marshalling.CaseString
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

  def endpointUrl = s"$TELEGRAM_API_URL${token.get}/$endpoint"

  def computedUrlParams: Map[String, String] =
    urlParams
      .filter {
        case (_, None) => false
        case _         => true
      }
      .map {
        case (key, Some(value)) => (CaseString(key).snakeCase, value.toString)
        case (key, value)       => (CaseString(key).snakeCase, value.toString)
      }

  def computedMultipartFormData: List[MultiItem] =
    multipartFormData.map {
      case (key, value) =>
        val file = new File(value)
        requests.MultiItem(key, file, file.getName)
    }.toList

  def call(): Try[T] = {
    val req = computedMultipartFormData match {
      case Nil => Try(request(endpointUrl, params = computedUrlParams))
      case _ =>
        Try(request(endpointUrl, params = computedUrlParams, data = requests.MultiPart(computedMultipartFormData: _*)))
    }
    req match {
      case Success(response) =>
        val json = parse(response.text()).getOrElse(Json.Null)
        json.findAllByKey("ok") match {
          case list: List[Json] if list.nonEmpty && list.head.toString() == "true" =>
            parseSuccessResponse(json.findAllByKey("result").head)
          case _ => Failure(decode[TelegramError](json.toString()).getOrElse(null))
        }
      case Failure(e) => Failure(e)
    }
  }
}
