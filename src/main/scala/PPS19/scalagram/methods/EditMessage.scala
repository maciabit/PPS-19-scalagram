package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, ReplyMarkup}
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.parser.decode
import io.circe.{Encoder, Json}
import requests.Requester

import scala.util.{Failure, Success, Try}

/** Use this method to edit text messages.
  * On success, the edited [[TelegramMessage]] is returned.
  *
  * @param token           Token of the bot that will perform the requets.
  * @param chatId          Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  * @param text            Text of the message to be sent, 1-4096 characters after entities parsing.
  * @param messageId       Required if inline_message_id is not specified. Identifier of the message to edit.
  * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message.
  * @param parseMode       Mode for parsing entities in the message text.
  *                        See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
  * @param entities        List of special entities that appear in message text, which can be specified instead of parse_mode.
  * @param disablePreview  Disables link previews for links in this message.
  * @param replyMarkup     Additional interface options.
  *                        Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
  *                        [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
  */
case class EditMessage(
    token: BotToken,
    chatId: ChatId,
    text: String,
    messageId: Option[Integer] = None,
    inlineMessageId: Option[String] = None,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disablePreview: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
) extends TelegramRequest[TelegramMessage] {

  val request: Requester = requests.post

  val endpoint: String = "editMessageText"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "text" -> text,
    "message_id" -> messageId,
    "inline_message_id" -> inlineMessageId,
    "parse_mode" -> parseMode,
    "entities" -> entities,
    "disable_web_page_preview" -> disablePreview,
    "reply_markup" -> (replyMarkup match {
      case Some(markup) =>
        Encoder[ReplyMarkup].snakeCase(markup).toString.filter(_ >= ' ')
      case None => None
    })
  )

  def parseSuccessfulResponse(json: Json): Try[TelegramMessage] =
    decode[TelegramMessage](json.toString()) match {
      case Right(message) => Success(message)
      case Left(error)    => Failure(error)
    }
}
