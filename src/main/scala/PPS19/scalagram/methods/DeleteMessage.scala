package PPS19.scalagram.methods

import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class DeleteMessage(chatId: Either[String,Int], messageId: Int) extends TelegramRequest[Boolean] {

  val request: Requester = requests.get

  val endpoint: String = "deleteMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
    "message_id" -> messageId,
  )

  def parseSuccessResponse(json: Json): Try[Boolean] = Success(true)
}