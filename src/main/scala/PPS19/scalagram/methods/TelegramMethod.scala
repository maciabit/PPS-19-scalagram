package PPS19.scalagram.methods

import PPS19.scalagram.methods.HttpMethod.HttpMethod
import PPS19.scalagram.utils.Props
import PPS19.scalagram.marshalling.CaseString

trait TelegramMethod {

  val TELEGRAM_API_URL = "https://api.telegram.org/bot"

  implicit class MapToUrlParams(map: Map[String, AnyVal]) {
    def toUrlParams: String = (map map {
      case (key, value) => s"${CaseString(key).snakeCase}=${value.toString}"
    }).mkString(",")
  }

  def method(httpMethod: HttpMethod, endpoint: String, token: String, urlParams: Map[String, AnyVal]): Unit = httpMethod match {
    //case HttpMethod.GET => requests.get(s"$TELEGRAM_API_URL/${Props.get("token")}/$endpoint?chat_id="+peer.chatId+"&text="+message)
    case HttpMethod.POST => requests.post
    case HttpMethod.PUT => requests.put
    case HttpMethod.DELETE => requests.delete

  }

}

object TelegramMethod {

}
