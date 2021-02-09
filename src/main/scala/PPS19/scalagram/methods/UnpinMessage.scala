package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class UnpinMessage(
    token: BotToken,
    chatId: Either[String, Int],
    messageId: Int
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "unpinChatMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
    "message_id" -> messageId
  )

  def parseSuccessResponse(json: Json): Try[Boolean] = Success(true)
}
