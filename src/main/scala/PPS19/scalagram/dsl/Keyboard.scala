package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.Conversions.{buttonToEither, listToEither, stringListToButtonEither, stringToButtonEither}
import PPS19.scalagram.dsl.KeyboardButtonUtils.KeyboardButtonList
import PPS19.scalagram.dsl.StringUtils.StringList
import PPS19.scalagram.dsl.ReplyKeyboardMarkupUtils.ReplyKeyboardMarkupOptions
import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, KeyboardButton, ReplyKeyboardMarkup, ReplyMarkup}
import PPS19.scalagram.utils.Props
import io.circe.Encoder

object ReplyKeyboardMarkupUtils {
  implicit class ReplyKeyboardMarkupOptions(markup: ReplyKeyboardMarkup) {
    def withResize: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(
        markup.keyboard,
        Some(true),
        markup.oneTimeKeyboard,
        markup.selective
      )
    def withOneTime: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(
        markup.keyboard,
        markup.resizeKeyboard,
        Some(true),
        markup.selective
      )
    def withSelective: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(
        markup.keyboard,
        markup.resizeKeyboard,
        markup.oneTimeKeyboard,
        Some(true)
      )
  }
}

object KeyboardButtonUtils {
  implicit class KeyboardButtonList(button: KeyboardButton) {
    def ::(button2: KeyboardButton) = List(button2, button)
  }
}

object StringUtils {
  implicit class StringList(string: String) {
    def ::(string2: String) = List(string2, string)
  }
}

object Keyboard {

  def apply(rows: Either[KeyboardButton, List[KeyboardButton]]*): ReplyKeyboardMarkup = {
    val keyboard = rows.map {
      case Left(button) => List(button)
      case Right(tuple) => tuple
    }
    ReplyKeyboardMarkup(keyboard, None, None, None)
  }

}

object InlineKeyboard {}

object TryKeyboard extends App {

  val keyboard = Keyboard(
    "Button 1",
    "Button 2" :: "Button 3"
  )

  println(keyboard)

  println(Encoder[ReplyMarkup].snakeCase(keyboard))

  val bot = Bot(BotToken(Props.get("token")))
  val chatId = ChatId("-1001286594106")
  bot.sendMessage(chatId, "Test keyboard DSL", replyMarkup = Some(keyboard))

}
