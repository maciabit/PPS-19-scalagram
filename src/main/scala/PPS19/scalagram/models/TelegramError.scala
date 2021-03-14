package PPS19.scalagram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Represents a Telegram error that can be caused by a bad request or a connection problem.
  *
  * @param statusCode  The status code of the error.
  * @param description The description of the error.
  * @param ok          The result of the request.
  */
case class TelegramError(statusCode: Int, description: String, ok: Boolean) extends Throwable

/** Companion object for TelegramError. Used as container for implicit methods. */
object TelegramError {
  implicit val telegramErrorDecoder: Decoder[TelegramError] =
    deriveDecoder[TelegramError]
  implicit val connectionError: TelegramError = TelegramError(
    404,
    "Unable to connect to the Internet. Check the status of your network and try again.",
    ok = false
  )
}
