package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.item.keyboard.KeyboardButtonContainer.{Callback, CurrentChatInlineQuery, InlineQuery, Url}
import PPS19.scalagram.dsl.item.keyboard.KeyboardUtils.{InlineKeyboard, Keyboard, buttonContainerToButtonRow, stringToButtonContainer, stringToButtonRow}
import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.models.ChatId
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DSLSuite extends AnyFunSuite {

  val bot: Bot = Bot(BotToken(Props.get("token")))
  val chatId: ChatId = ChatId("-1001286594106")

  test("A Reply Keyboard can be created with the DSL and sent in a message") {
    val keyboard = Keyboard(
      "Button 1",
      "Button 2" :: "Button 3"
    )
    val res = bot.sendMessage(chatId, "Test ReplyKeyboard DSL", replyMarkup = Some(keyboard))
    assert(res.isSuccess)
  }

  test("An Inline Keyboard can be created with the DSL and sent in a message") {
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
    val res = bot.sendMessage(chatId, "Test InlineKeyboard DSL", replyMarkup = Some(inlineKeyboard))
    assert(res.isSuccess)
  }

}
