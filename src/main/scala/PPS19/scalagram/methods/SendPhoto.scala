package PPS19.scalagram.methods

import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.messages.TelegramMessage
import PPS19.scalagram.models.{BotToken, _}
import io.circe.parser.decode
import io.circe.{Encoder, Json}
import requests.Requester

import scala.util.{Failure, Success, Try}

/** Use this method to send photos. On success, the sent [[TelegramMessage]] is returned.
  *
  * @param token                    Token of the bot that will perform the requets.
  * @param chatId                   Unique identifier for the target chat or username of the target channel (in the format @channelusername).
  * @param photo                    Photo to send.
  *                                 Pass a file_id as String to send a photo that exists on the Telegram servers (recommended),
  *                                 pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using multipart/form-data.
  * @param caption                  Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing.
  * @param parseMode                Mode for parsing entities in the message text.
  *                                 See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
  * @param entities                 List of special entities that appear in message text, which can be specified instead of parse_mode.
  * @param disableNotification      Sends the message silently. Users will receive a notification with no sound.
  * @param replyToMessageId         If the message is a reply, ID of the original message.
  * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found.
  * @param replyMarkup              Additional interface options.
  *                                 Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
  *                                 [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
  */
case class SendPhoto(
    token: BotToken,
    chatId: ChatId,
    photo: InputFile,
    caption: Option[String] = None,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
) extends TelegramRequest[TelegramMessage] {

  val request: Requester = requests.post

  val endpoint: String = "sendPhoto"

  val urlParams: Map[String, Any] = Map(
    "chat_id" -> chatId.get,
    "photo" -> (photo match {
      case ExistingMedia(fileId) => fileId
      case RemoteMedia(url)      => url
      case UploadMedia(_)        => None
    }),
    "caption" -> caption,
    "parse_mode" -> parseMode,
    "entities" -> entities,
    "disable_notification" -> disableNotification,
    "reply_to_message_id" -> replyToMessageId,
    "allow_sending_without_reply" -> allowSendingWithoutReply,
    "reply_markup" -> (replyMarkup match {
      case Some(markup) =>
        Encoder[ReplyMarkup].snakeCase(markup).toString.filter(_ >= ' ')
      case None => None
    })
  )

  override val multipartFormData: Map[String, String] = Map(
    "photo" -> (photo match {
      case UploadMedia(path) => path
      case _                 => null
    })
  ).filter(item => item._2 != null)

  def parseSuccessfulResponse(json: Json): Try[TelegramMessage] =
    decode[TelegramMessage](json.toString()) match {
      case Right(message) => Success(message)
      case Left(error)    => Failure(error)
    }
}
