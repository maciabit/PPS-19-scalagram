package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.models.HttpMethod
import PPS19.scalagram.models.HttpMethod.HttpMethod
import PPS19.scalagram.utils.Props
import requests.Response

import scala.util.{Failure, Try}

object TelegramMethod {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  def method1(httpMethod: HttpMethod, endpoint: String)(urlParams: Map[String, Any]): Try[Response] = {
    val url = s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}"
    httpMethod match {
      case HttpMethod.GET => try {Try(requests.get(url))} catch {case e: requests.UnknownHostException => Failure(e)}
      case HttpMethod.POST => try {Try(requests.get(url))} catch {case e: requests.UnknownHostException => Failure(e)}
      case HttpMethod.PUT => try {Try(requests.get(url))} catch {case e: requests.UnknownHostException => Failure(e)}
      case HttpMethod.DELETE => try {Try(requests.get(url))} catch {case e: requests.UnknownHostException => Failure(e)}
    }
  }

  def method(httpMethod: HttpMethod, endpoint: String)(urlParams: Map[String, Any]): Response = httpMethod match {
    case HttpMethod.GET => requests.post(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.POST => requests.post(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.PUT => requests.put(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.DELETE => requests.delete(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
  }

}
