package PPS19.scalagram.methods

import requests.Response

case class UnpinAllMessages() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "unpinAllChatMessages")
  def unpinAllMessages(chatId: Either[String,Int]): Boolean = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
    )
    val res = method(urlParams)
    res.statusCode == 200
  }
}
