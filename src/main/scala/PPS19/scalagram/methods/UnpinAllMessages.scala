package PPS19.scalagram.methods

import io.circe.Json

import scala.util.{Failure, Success, Try}

case class UnpinAllMessages() {

  private val method: Map[String, Any] => Try[Json] = telegramApiRequest(requests.post, "unpinAllChatMessages")

  def unpinAllMessages(chatId: Either[String,Int]): Try[Boolean] = {
    val urlParams: Map[String, Any] = Map(
      "chat_id" -> chatId.fold(l => l, r => r),
    )
    val res = method(urlParams)
    res match {
      case Success(_) => Success(true)
      case Failure(error) => Failure(error)
    }
  }
}
