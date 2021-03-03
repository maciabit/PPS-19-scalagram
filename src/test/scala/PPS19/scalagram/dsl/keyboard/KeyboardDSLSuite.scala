package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.dsl._
import PPS19.scalagram.marshalling.codecs.EncoderOps
import PPS19.scalagram.models._
import io.circe.Encoder
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class KeyboardDSLSuite extends AnyFunSuite {

  test("A Reply Keyboard created with the DSL equals one created without it") {
    val dslKeyboard = Keyboard(
      "Button 1",
      "Button 2" :: "Button 3"
    )
    val standardKeyboard = ReplyKeyboardMarkup(
      Seq(
        Seq(ReplyKeyboardButton("Button 1")),
        Seq(
          ReplyKeyboardButton("Button 2"),
          ReplyKeyboardButton("Button 3")
        )
      )
    )
    assert(Encoder[ReplyMarkup].snakeCase(dslKeyboard) == Encoder[ReplyMarkup].snakeCase(standardKeyboard))
  }

  test("An Inline Keyboard created with the DSL equals one created without it") {
    val dslInlineKeyboard = InlineKeyboard(
      "Button 1",
      "Button 2" :: "Button 3",
      Callback("Callback button" -> "data"),
      Url("Link1" -> "http://www.google.it") :: Url("Link2" -> "http://www.youtube.com"),
      Callback("Callback button" -> "data") :: "Button",
      "Button" :: Callback("Callback button" -> "data"),
      InlineQuery("Inline query" -> "asd"),
      CurrentChatInlineQuery("Inline query current chat" -> "asd")
    )
    val standardInlineKeyboard = InlineKeyboardMarkup(
      Seq(
        Seq(InlineKeyboardButton("Button 1", callbackData = Some("Button 1"))),
        Seq(
          InlineKeyboardButton("Button 2", callbackData = Some("Button 2")),
          InlineKeyboardButton("Button 3", callbackData = Some("Button 3"))
        ),
        Seq(InlineKeyboardButton.callback("Callback button", "data")),
        Seq(
          InlineKeyboardButton.url("Link1", "http://www.google.it"),
          InlineKeyboardButton.url("Link2", "http://www.youtube.com")
        ),
        Seq(
          InlineKeyboardButton.callback("Callback button", "data"),
          InlineKeyboardButton("Button", callbackData = Some("Button"))
        ),
        Seq(
          InlineKeyboardButton("Button", callbackData = Some("Button")),
          InlineKeyboardButton.callback("Callback button", "data")
        ),
        Seq(InlineKeyboardButton.switchInlineQuery("Inline query", "asd")),
        Seq(InlineKeyboardButton.switchInlineQueryCurrentChat("Inline query current chat", "asd"))
      )
    )
    assert(Encoder[ReplyMarkup].snakeCase(dslInlineKeyboard) == Encoder[ReplyMarkup].snakeCase(standardInlineKeyboard))
  }
}
