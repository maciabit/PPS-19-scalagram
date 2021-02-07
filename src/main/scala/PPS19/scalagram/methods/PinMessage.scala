package PPS19.scalagram.methods

import io.circe.Json
import requests.Requester

import scala.util.{Success, Try}

case class PinMessage(
  chatId: Either[String,Int],
  messageId: Int,
  disableNotification: Option[Boolean]
) extends TelegramRequest[Boolean] {

  val request: Requester = requests.post

  val endpoint: String = "pinChatMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.fold(l => l, r => r),
    "message_id" -> messageId,
    "disable_notification" -> disableNotification
  )

  def parseSuccessResponse(json: Json): Try[Boolean] = Success(true)
}