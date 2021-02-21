package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.models.ChatId
import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class UnpinAllMessages(token: BotToken, chatId: ChatId) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "unpinAllChatMessages"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get
  )

  def parseSuccessResponse(json: Json): Try[Boolean] = Success(true)
}
