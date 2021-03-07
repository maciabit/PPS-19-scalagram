package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.models.ChatId
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class DeleteMessage(
    token: BotToken,
    chatId: ChatId,
    messageId: Int
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.get

  val endpoint: String = "deleteMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "message_id" -> messageId
  )

  def parseSuccessfulResponse(json: Json): Try[Boolean] = Success(true)
}
