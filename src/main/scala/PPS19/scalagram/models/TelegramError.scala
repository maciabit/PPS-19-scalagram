package PPS19.scalagram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class TelegramError(statusCode: Int, description: String, ok: Boolean) extends Throwable

object TelegramError {
  implicit val telegramErrorDecoder: Decoder[TelegramError] = deriveDecoder[TelegramError]
}
