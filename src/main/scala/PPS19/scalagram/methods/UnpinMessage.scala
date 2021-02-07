package PPS19.scalagram.methods

import io.circe.Json

import scala.util.{Failure, Success, Try}

case class UnpinMessage() {

  private val method: Map[String, Any] => Try[Json] = telegramApiRequest(requests.post, "unpinChatMessage")

  def call(chatId: Either[String,Int], messageId: Int): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
      "message_id" -> messageId,
    )
    val res = method(urlParams)
    res match {
      case Success(_) => Success(true)
      case Failure(error) => Failure(error)
    }
  }
}
