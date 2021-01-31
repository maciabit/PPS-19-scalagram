package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.models.{HttpMethod, TelegramError}
import PPS19.scalagram.models.HttpMethod.HttpMethod
import PPS19.scalagram.utils.Props
import requests.{Response, UnknownHostException}

import scala.util.{Failure, Success, Try}

object TelegramMethod {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  def method1(httpMethod: HttpMethod, endpoint: String)(urlParams: Map[String, Any]): Try[Response] = {
    val url = s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}"
    httpMethod match {
      case HttpMethod.GET => Try(requests.get(url)) match {
        case Success(i) => Success(i)
        case Failure(e) => println(e); Failure(e)
      }
      case HttpMethod.POST => Try(requests.post(url)) match {
        case Success(i) => Success(i)
        case Failure(e) => println(e); Failure(e)
      }
      case HttpMethod.PUT => Try(requests.put(url)) match {
        case Success(i) => Success(i)
        case Failure(e) => println(e); Failure(e)
      }
      case HttpMethod.DELETE => Try(requests.delete(url)) match {
        case Success(i) => Success(i)
        case Failure(e) => println(e); Failure(e)
      }
    }
  }

  def method(httpMethod: HttpMethod, endpoint: String)(urlParams: Map[String, Any]): Response = httpMethod match {
    case HttpMethod.GET => requests.post(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.POST => requests.post(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.PUT => requests.put(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.DELETE => requests.delete(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
  }

}
