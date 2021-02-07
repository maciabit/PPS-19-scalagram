package PPS19.scalagram.methods

import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class UnpinAllMessages(chatId: Either[String, Int]) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "unpinAllChatMessages"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
  )

  def parseSuccessResponse(json: Json): Try[Boolean] = Success(true)
}
