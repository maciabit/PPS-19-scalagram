package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.dsl.keyboard.StringUtils.StringList
import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, InlineKeyboardButton, InlineKeyboardMarkup, KeyboardButton, ReplyMarkup}
import PPS19.scalagram.utils.Props
import io.circe.Encoder

object InlineKeyboard {

  def apply(rows: Either[KeyboardButton, List[KeyboardButton]]*): InlineKeyboardMarkup = {
    InlineKeyboardMarkup(keyboardButtonToInline(rows)(_ => InlineKeyboardButton("Pisto")))
  }

  private def keyboardButtonToInline(buttons: Seq[Either[KeyboardButton, List[KeyboardButton]]])(conversion: KeyboardButton => InlineKeyboardButton):
  Seq[Seq[InlineKeyboardButton]] =
    buttons.map {
      case Left(button) => List(conversion(button))
      case Right(tuple) => tuple.map(conversion)
    }
}

object TryInlineKeyboard extends App {
  import PPS19.scalagram.dsl.keyboard.Conversion._

  val keyboard = InlineKeyboard(
    "Button 1",
    "Button 2" :: "Button 3"
  )

  println(Encoder[ReplyMarkup].snakeCase(keyboard))

  val bot = Bot(BotToken(Props.get("token")))
  val chatId = ChatId("-1001286594106")
  bot.sendMessage(chatId, "Test InlineKeyboard DSL", replyMarkup = Some(keyboard))

}
