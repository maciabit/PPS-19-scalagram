package PPS19.scalagram.methods

import requests.Requester

import scala.util.{Failure, Success, Try}

case class UnpinAllMessages(chatId: Either[String, Int]) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "unpinAllChatMessages"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
  )

  def call(): Try[Boolean] = perform() match {
    case Success(_) => Success(true)
    case Failure(error) => Failure(error)
  }
}
