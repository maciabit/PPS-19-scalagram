package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class KeyboardButton(
    text: String,
    requestContact: Option[Boolean] = None,
    requestLocation: Option[Boolean] = None
)

object KeyboardButton {
  implicit val keyboardButtonEncoder: Encoder[KeyboardButton] = deriveEncoder
}
