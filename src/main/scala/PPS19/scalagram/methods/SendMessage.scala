package PPS19.scalagram.methods

import PPS19.scalagram.utils.Props
import io.circe.Json
import requests.Response

case class SendMessage(){
  val method: Map[String, Any] => Response = TelegramMethod.method(HttpMethod.POST, "sendMessage")
  def sendMessage(chatId: Either[String, Int],
                  text: String,
                  parseMode: Option[String],
                  entities: Option[Vector[Any]] = None,
                  disablePreview: Option[Boolean] = None,
                  disableNotification: Option[Boolean] = None,
                  replyToMessageId: Option[Int],
                  allowSendingWithoutReply: Option[Boolean],
                  replyMarkup: Option[Markup] = None): Json = {
    val urlParams: Map[String, Any] = Map {
      "chat_id" -> chatId,
      "text" -> text,
      "parse_mode" -> parseMode,
      "entities" -> entities,
      "disable_web_page_preview" -> disablePreview,
      "disable_notification" -> disableNotification,
      "reply_to_message_id" -> replyToMessageId,
      "allow_sending_without_reply" -> allowSendingWithoutReply,
      "reply_markup" -> replyMarkup,
    } filter { case (_, value) => value.isDefined } map { case (key, value) => (key, value.get) }
    val res = method(urlParams)

  }

  def sendMessage(): Int = {
    val req = requests.post("https://api.telegram.org/bot"+Props.get("token")+"/sendMessage?chat_id="+peer.chatId+"&text="+message)
    req.statusCode
  }
}
