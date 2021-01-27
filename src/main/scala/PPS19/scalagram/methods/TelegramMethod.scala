package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.methods.HttpMethod.HttpMethod
import PPS19.scalagram.utils.Props
import requests.Response

object TelegramMethod {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  def method(httpMethod: HttpMethod, endpoint: String)(urlParams: Map[String, Any]): Response = httpMethod match {
    case HttpMethod.GET => requests.get(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.POST => requests.post(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.PUT => requests.put(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.DELETE => requests.delete(s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
  }
}
