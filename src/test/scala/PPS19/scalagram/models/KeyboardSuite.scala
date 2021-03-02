package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.EncoderOps
import io.circe.{Encoder, Json, parser}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import PPS19.scalagram.models.Utils.getJsonString

@RunWith(classOf[JUnitRunner])
class KeyboardSuite extends AnyFunSuite {

  private def testReplyMarkupEncoding(r: ReplyMarkup, expectedJsonPath: String): Unit = {
    val keyboardJson = Encoder[ReplyMarkup].snakeCase(r)
    val expectedJsonString = getJsonString(expectedJsonPath)
    val expectedJson = parser.parse(expectedJsonString).getOrElse(Json.Null)
    assert(keyboardJson == expectedJson)
  }

  // Response Keyboard

  test("A single button Response Keyboard can be properly encoded") {
    testReplyMarkupEncoding(
      ReplyKeyboardMarkup(Seq(Seq(ReplyKeyboardButton("Button")))),
      s"reply_markup/SingleButtonKeyboard.json"
    )
  }

  test("A column of buttons Response Keyboard can be properly encoded") {
    testReplyMarkupEncoding(
      ReplyKeyboardMarkup(Seq(Seq(ReplyKeyboardButton("Button 1")), Seq(ReplyKeyboardButton("Button 2")))),
      s"reply_markup/ColumnOfButtonsKeyboard.json"
    )
  }

  test("A row of buttons Response Keyboard can be properly encoded") {
    testReplyMarkupEncoding(
      ReplyKeyboardMarkup(Seq(Seq(ReplyKeyboardButton("Button 1"), ReplyKeyboardButton("Button 2")))),
      s"reply_markup/RowOfButtonsKeyboard.json"
    )
  }

  test("A grid of buttons Response Keyboard can be properly encoded") {
    testReplyMarkupEncoding(
      ReplyKeyboardMarkup(
        Seq(
          Seq(ReplyKeyboardButton("Button 1"), ReplyKeyboardButton("Button 2")),
          Seq(ReplyKeyboardButton("Button 3"), ReplyKeyboardButton("Button 4"))
        )
      ),
      s"reply_markup/GridOfButtonsKeyboard.json"
    )
  }

  test("A Response Keyboard can be removed") {
    testReplyMarkupEncoding(ReplyKeyboardRemove(), s"reply_markup/RemoveKeyboard.json")
  }

  // Inline Keyboard

  test("URL Buttons, Callback Buttons and Switch to Inline Buttons can be properly encoded in an Inline Keyboard") {
    testReplyMarkupEncoding(
      InlineKeyboardMarkup(
        Seq(
          Seq(
            InlineKeyboardButton.url("Link", "https://www.youtube.com/"),
            InlineKeyboardButton.callback("Callback", "Callback"),
            InlineKeyboardButton.switchInlineQueryCurrentChat("Inline chat", "HI"),
            InlineKeyboardButton.switchInlineQuery("Inline query", "")
          )
        )
      ),
      s"reply_markup/CustomButtonsKeyboard.json"
    )
  }

  // Force reply

  test("A message that displays a reply interface to the user can be properly encoded") {
    testReplyMarkupEncoding(ForceReply(), s"reply_markup/ForceReply.json")
  }
}
