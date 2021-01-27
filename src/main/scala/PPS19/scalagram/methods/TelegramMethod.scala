package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.methods.HttpMethod.HttpMethod
import PPS19.scalagram.utils.Props

trait TelegramMethod {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"
  Props.load()

  def method(httpMethod: HttpMethod, endpoint: String, token: String, urlParams: Map[String, Any]): Unit = httpMethod match {
    case HttpMethod.GET => requests.get(s"$TELEGRAM_API_URL/${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.POST => requests.post(s"$TELEGRAM_API_URL/${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.PUT => requests.put(s"$TELEGRAM_API_URL/${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")
    case HttpMethod.DELETE => requests.delete(s"$TELEGRAM_API_URL/${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}")

  }
}

object TelegramMethod {

}
