package PPS19.scalagram.methods

import PPS19.scalagram.models.HttpMethod
import requests.Response

case class UnpinMessage() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "unpinChatMessage")
  def unpinMessage(chatId: Either[String,Int], messageId: Int): Boolean = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
    )
    val res = method(urlParams)
    res.statusCode == 200
  }
}
