package PPS19.scalagram.methods

import requests.Requester

import scala.util.{Failure, Success, Try}

case class DeleteMessage(chatId: Either[String,Int], messageId: Int) extends TelegramRequest[Boolean] {

  val request: Requester = requests.get

  val endpoint: String = "deleteMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
    "message_id" -> messageId,
  )

  def call(): Try[Boolean] = perform() match {
    case Success(_) => Success(true)
    case Failure(error) => Failure(error)
  }
}