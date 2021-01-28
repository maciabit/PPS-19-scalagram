package PPS19.scalagram.methods

import requests.Response

case class PinMessage() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "pinChatMessage")
  def pinMessage(chatId: Either[String,Int], messageId: Int, disableNotification: Option[Boolean]): Boolean = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
      "disable_notification" -> disableNotification
    ) filter {
      case (_, None) => false
      case _ => true
    } map {
      case (key, Some(value)) => (key, value)
        case(key,value) => (key,value)
    }
    val res = method(urlParams)
    res.statusCode == 200
  }
}
