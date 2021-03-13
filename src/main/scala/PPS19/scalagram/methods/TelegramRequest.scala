package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.CaseString
import PPS19.scalagram.models.{BotToken, TelegramError}
import io.circe.Json
import io.circe.parser.{decode, parse}
import requests.{MultiItem, Requester}

import java.io.File
import scala.util.{Failure, Success, Try}

/**
  * A request to the Telegram APIs.
  * @tparam T Type of the response returned from the request.
  */
trait TelegramRequest[T] {

  /** Official Telegram API URL. */
  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  /** Token of the bot that will make the request. */
  val token: BotToken

  /** Type of the request. */
  val request: Requester

  /** Request endpoint. */
  val endpoint: String

  /** Query parameters for the request. */
  val urlParams: Map[String, Any]

  /** Multipart form data for the request. */
  val multipartFormData: Map[String, String] = Map.empty

  /** Attempts to parse a successful response to the request.
    *
    * @param json Response JSON.
    * @return Response parsed into an object.
    */
  def parseSuccessfulResponse(json: Json): Try[T]

  /** Complete endpoint URL for the request, obtained by combining [[TelegramRequest.TELEGRAM_API_URL]], [[TelegramRequest.token]] and [[TelegramRequest.endpoint]]. */
  def endpointUrl = s"$TELEGRAM_API_URL${token.get}/$endpoint"

  /** Maps [[TelegramRequest.urlParams]] to snake case keys and string values, conforming to Telegram API standards. */
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

  /** Maps [[TelegramRequest.multipartFormData]] to a list of MultiItem objects. */
  def computedMultipartFormData: List[MultiItem] =
    multipartFormData.map {
      case (key, value) =>
        val file = new File(value)
        requests.MultiItem(key, file, file.getName)
    }.toList

  /** Executes the request.
    *
    * @return Response as parsed from [[TelegramRequest.parseSuccessfulResponse()]].
    */
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
            parseSuccessfulResponse(json.findAllByKey("result").head)
          case _ => Failure(decode[TelegramError](json.toString()).getOrElse(null))
        }
      case Failure(e) => Failure(e)
    }
  }
}
