package PPS19.scalagram.methods

import PPS19.scalagram.models.{ExistingMedia,InputFile, RemoteMedia,ReplyKeyboardRemove,TelegramError}
import PPS19.scalagram.models.messages.TelegramMessage
import io.circe.parser.{decode, parse}
import requests.Response
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ForceReply,InlineKeyboardMarkup,ReplyKeyboardMarkup, ReplyMarkup}
import io.circe.generic.semiauto.deriveEncoder
import io.circe.{Encoder, Json}


import scala.util.{Failure, Success, Try}

case class SendPhoto(){
  val method: Map[String, Any] => Try[Response] = TelegramRequest.telegramApiRequest(requests.post, "sendPhoto")
  def sendPhoto(chatId: Either[String, Int],
                  photo: InputFile,
                  caption: Option[String] = None,
                  parseMode: Option[String] = None,
                  entities: Option[Vector[Any]] = None,
                  disableNotification: Option[Boolean] = None,
                  replyToMessageId: Option[Int] = None,
                  allowSendingWithoutReply: Option[Boolean] = None,
                  replyMarkup: Option[ReplyMarkup] = None): Try[TelegramMessage] = {
    val urlParams: Map[String, Any] = Map (
      "chat_id" -> chatId.fold(l => l, r => r),
      "photo" -> (photo match {
        case ExistingMedia(fileId) => fileId
        case RemoteMedia(url) => url
      }),
      "caption" -> caption,
      "parse_mode" -> parseMode,
      "entities" -> entities,
      "disable_notification" -> disableNotification,
      "reply_to_message_id" -> replyToMessageId,
      "allow_sending_without_reply" -> allowSendingWithoutReply,
      "reply_markup" -> replyMarkup match {
        case (k, Some(markup : InlineKeyboardMarkup)) => {
          val encoder: Encoder[InlineKeyboardMarkup] = deriveEncoder
          (k, encoder.snakeCase(markup).toString().filter(_ >= ' '))
        }
        case (k, Some(markup : ReplyKeyboardMarkup)) => {
          val encoder: Encoder[ReplyKeyboardMarkup] = deriveEncoder
          (k, encoder.snakeCase(markup).toString().filter(_ >= ' '))
        }
        case (k, Some(markup : ReplyKeyboardRemove)) => {
          val encoder: Encoder[ReplyKeyboardRemove] = deriveEncoder
          (k, encoder.snakeCase(markup).toString().filter(_ >= ' '))
        }
        case (k, Some(markup : ForceReply)) => {
          val encoder: Encoder[ForceReply] = deriveEncoder
          (k, encoder.snakeCase(markup).toString().filter(_ >= ' '))
        }
        case (k, None) => (k, None)
      }
    ) filter {
      case (_, None) => false
      case _ => true
    } map {
      case (key, Some(value)) => (key, value)
      case (key, value) => (key, value)
    }
    val res = method(urlParams)
    res match {
      case Success(response) =>
        val parsed = parse(response.text()).getOrElse(Json.Null)
        parsed.findAllByKey("ok").head.toString() match {
          case "false" => Failure(decode[TelegramError](parsed.toString()).getOrElse(null))
          case "true" =>
            decode[TelegramMessage](parsed.findAllByKey("result").head.toString()) match {
              case Right(message) => Success(message)
              case Left(error) => Failure(error)
            }
        }
      case Failure(e) => Failure(e)
    }
  }
}
