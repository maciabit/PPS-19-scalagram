package PPS19.scalagram.dsl.item.keyboard

import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, ReplyKeyboardButton, ReplyKeyboardMarkup, ReplyMarkup}
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

object Keyboard {

  def apply(rows: Either[ReplyKeyboardButton, List[ReplyKeyboardButton]]*): ReplyKeyboardMarkup = {
    val keyboard = rows.map {
      case Left(button) => List(button)
      case Right(tuple) => tuple
    }
    ReplyKeyboardMarkup(keyboard, None, None, None)
  }
}

object TryKeyboard extends App {
  import PPS19.scalagram.dsl.item.keyboard.Utils._
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
