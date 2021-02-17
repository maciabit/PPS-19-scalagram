package PPS19.scalagram.dsl.item.keyboard

import PPS19.scalagram.dsl.item.keyboard.KeyboardButtonContainer._
import PPS19.scalagram.dsl.item.keyboard.KeyboardUtils.{InlineKeyboard, Keyboard, buttonContainerToButtonRow, stringToButtonContainer, stringToButtonRow}
import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models.{ChatId, ReplyMarkup}
import PPS19.scalagram.utils.Props
import io.circe.Encoder

trait KeyboardRow {

  def buttons: Seq[KeyboardButtonContainer]

  def ::(button: KeyboardButtonContainer): KeyboardRow
}

case class KeyboardRowImpl(buttons: Seq[KeyboardButtonContainer]) extends KeyboardRow {

  override def ::(button: KeyboardButtonContainer): KeyboardRow = KeyboardRowImpl(buttons :+ button)
}

object TryKeyboardButtonContainer extends App {

  val keyboard = Keyboard(
    "Button 1",
    "Button 2" :: "Button 3"
  )

  val inlineKeyboard = InlineKeyboard(
    "Button 1",
    "Button 2" :: "Button 3",
    Callback("Callback button" -> "data"),
    Url("Link1" -> "http://www.google.it") :: Url("Link2" -> "http://www.youtube.com"),
    Callback("Callback button" -> "data") :: "Button",
    "Button" :: Callback("Callback button" -> "data"),
    InlineQuery("Inline query" -> "asd"),
    CurrentChatInlineQuery("Inline query current chat" -> "asd")
  )

  println(Encoder[ReplyMarkup].snakeCase(keyboard))
  println(Encoder[ReplyMarkup].snakeCase(inlineKeyboard))

  val bot = Bot(BotToken(Props.get("token")))
  val chatId = ChatId("-1001286594106")
  val res1 = bot.sendMessage(chatId, "Test ReplyKeyboard DSL", replyMarkup = Some(keyboard))
  val res2 = bot.sendMessage(chatId, "Test InlineKeyboard DSL", replyMarkup = Some(inlineKeyboard))
  println(res1)
  println(res2)
}