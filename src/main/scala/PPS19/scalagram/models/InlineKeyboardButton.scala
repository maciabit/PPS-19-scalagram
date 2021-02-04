package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

/*MUST use exactly one of the optional fields!*/
case class InlineKeyboardButton(
  text: String,
  url: Option[String] = None,
  //login_url: Option[LoginUrl] = None,
  callbackData: Option[String] = None,
  switchInlineQuery: Option[String] = None,
  switchInlineQueryCurrentChat: Option[String] = None,
  pay: Option[Boolean] = None
)

object InlineKeyboardButton{
  implicit val inlineKeyboardButtonEncoder : Encoder[InlineKeyboardButton] = deriveEncoder
}
