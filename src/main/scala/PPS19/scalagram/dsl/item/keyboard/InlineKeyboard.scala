package PPS19.scalagram.dsl.item.keyboard

import PPS19.scalagram.dsl.item.keyboard.InlineKeyboard.{Callback, Url}
import PPS19.scalagram.dsl.item.keyboard.StringUtils.StringList
import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, InlineKeyboardButton, InlineKeyboardMarkup, ReplyMarkup}
import PPS19.scalagram.utils.Props
import io.circe.Encoder

object InlineKeyboard {

  def apply(rows: Either[InlineKeyboardButton, List[InlineKeyboardButton]]*): InlineKeyboardMarkup = {
    val keyboard = rows.map {
      case Left(button) => List(button)
      case Right(tuple) => tuple
    }
    InlineKeyboardMarkup(keyboard)
  }

  def Callback(tuple: (String, String)): InlineKeyboardButton =
    InlineKeyboardButton(tuple._1, callbackData = Some(tuple._2))

  def Url(tuple: (String, String)): InlineKeyboardButton =
    InlineKeyboardButton(tuple._1, url = Some(tuple._2))
}

object TryInlineKeyboard extends App {
  import PPS19.scalagram.dsl.item.keyboard.Utils._

  val keyboard = InlineKeyboard(
    Callback("Button 1" -> "callabsafnjds"),
    Url("Button 2" -> "www.google.it"),
    Callback("Button 3" -> "Hello button 3") :: Url("Button 4" -> "www.google.it"),
    "ButtonLone".::(Callback("Button 3" -> "Hello button 3"))
  )

  println(Encoder[ReplyMarkup].snakeCase(keyboard))

  val bot = Bot(BotToken(Props.get("token")))
  val chatId = ChatId("-1001286594106")
  val res = bot.sendMessage(chatId, "Test InlineKeyboard DSL", replyMarkup = Some(keyboard))
}
