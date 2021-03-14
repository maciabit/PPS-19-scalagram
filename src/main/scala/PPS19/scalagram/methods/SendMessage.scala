package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.payloads.TelegramMessage
import PPS19.scalagram.models.{BotToken, ChatId, ReplyMarkup}
import io.circe.parser._
import io.circe.{Encoder, Json}
import requests.Requester

import scala.util.{Failure, Success, Try}

/** Use this method to send text messages. On success, the sent [[PPS19.scalagram.models.payloads.TelegramMessage]] is returned.
  *
  * @param token                    Token of the bot that will perform the requets.
  * @param chatId                   Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  * @param text                     Text of the message to be sent, 1-4096 characters after entities parsing.
  * @param parseMode                Mode for parsing entities in the message text.
  *                                 See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
  * @param entities                 List of special entities that appear in message text, which can be specified instead of parse_mode.
  * @param disablePreview           Disables link previews for links in this message.
  * @param disableNotification      Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId         If the message is a reply, ID of the original message.
  * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found.
  * @param replyMarkup              Additional interface options.
  *                                 Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
  *                                 [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
  */
case class SendMessage(
    token: BotToken,
    chatId: ChatId,
    text: String,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disablePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
) extends TelegramRequest[TelegramMessage] {

  val request: Requester = requests.post

  val endpoint: String = "sendMessage"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "text" -> text,
    "parse_mode" -> parseMode,
    "entities" -> entities,
    "disable_web_page_preview" -> disablePreview,
    "disable_notification" -> disableNotification,
    "reply_to_message_id" -> replyToMessageId,
    "allow_sending_without_reply" -> allowSendingWithoutReply,
    "reply_markup" -> (replyMarkup match {
      case Some(markup) => Encoder[ReplyMarkup].snakeCase(markup).toString.filter(_ >= ' ')
      case None         => None
    })
  )

  def parseSuccessfulResponse(json: Json): Try[TelegramMessage] =
    decode[TelegramMessage](json.toString()) match {
      case Right(message) => Success(message)
      case Left(error)    => Failure(error)
    }
}
