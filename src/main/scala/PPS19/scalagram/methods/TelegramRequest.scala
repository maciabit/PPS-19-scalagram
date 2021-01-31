package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.MapUtils.MapToUrlParams
import PPS19.scalagram.utils.Props
import requests.{Requester, Response}
import scala.util.{Failure, Success, Try}

object TelegramRequest {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  def telegramApiRequest(request: Requester, endpoint: String)(urlParams: Map[String, Any]): Try[Response] = {
    val url = s"$TELEGRAM_API_URL${Props.get("token")}/$endpoint?${urlParams.toUrlQuery}"
    Try(request(url)) match {
      case Success(i) => Success(i)
      case Failure(e) => println(e); Failure(e)
    }
  }
}
