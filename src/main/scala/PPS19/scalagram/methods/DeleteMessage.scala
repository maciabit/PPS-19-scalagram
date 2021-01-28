package PPS19.scalagram.methods

import requests.Response

case class DeleteMessage() {
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.GET, "deleteMessage")
  def deleteMessage(chatId: Either[String,Int], messageId: Int): Boolean = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
    )
    val res = method(urlParams)
    res.statusCode == 200
  }
}
