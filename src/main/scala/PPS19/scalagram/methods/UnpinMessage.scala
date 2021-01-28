package PPS19.scalagram.methods

import requests.Response

case class UnpinMessage() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.GET, "unpinChatMessage")
  def unpinMessage(chatId: Either[String,Int], messageId: Int): Boolean = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
    )
    val res = method(urlParams)
    res.statusCode == 200
  }
}
