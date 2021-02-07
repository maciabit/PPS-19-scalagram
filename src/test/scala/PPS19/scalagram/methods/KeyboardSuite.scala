package PPS19.scalagram.methods

import PPS19.scalagram.models.{ForceReply, InlineKeyboardButton, InlineKeyboardMarkup, KeyboardButton, ReplyKeyboardMarkup, ReplyKeyboardRemove}
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class KeyboardSuite extends AnyFunSuite with BeforeAndAfter with BeforeAndAfterEach {

  val groupChatId = Left("-1001286594106")

  before {
    Props.load()
  }

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  // Response Keyboard

  test("A message with a single button Response Keyboard can be sent") {
    val keyboard = Some(ReplyKeyboardMarkup(Seq(Seq(KeyboardButton("Button")))))
    val message = SendMessage(groupChatId, "Single button keyboard", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }

  test("A message with a column of buttons Response Keyboard can be sent") {
    val keyboard = Some(ReplyKeyboardMarkup(Seq(Seq(KeyboardButton("Button 1"), KeyboardButton("Button 2")))))
    val message = SendMessage(groupChatId, "Row of buttons keyboard", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }

  test("A message with a row of buttons Response Keyboard can be sent") {
    val keyboard = Some(ReplyKeyboardMarkup(Seq(Seq(KeyboardButton("Button 1")), Seq(KeyboardButton("Button 2")))))
    val message = SendMessage(groupChatId, "Column of buttons keyboard", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }

  test("A message with a grid of buttons Response Keyboard can be sent") {
    val keyboard = Some(ReplyKeyboardMarkup(Seq(
      Seq(KeyboardButton("Button 1"), KeyboardButton("Button 2")),
      Seq(KeyboardButton("Button 3"), KeyboardButton("Button 4"))
    )))
    val message = SendMessage(groupChatId, "Grid of buttons keyboard", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }

  test("A Response Keyboard can be removed") {
    val keyboard = Some(ReplyKeyboardRemove())
    val message = SendMessage(groupChatId, "Removing response keyboard", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }

  // Inline Keyboard

  test("URL Buttons, Callback Buttons and Switch to Inline Buttons can be sent in an Inline Keyboard") {
    val keyboard = Some(InlineKeyboardMarkup(Seq(Seq(
      // Link button
      InlineKeyboardButton("Link", url = Some("https://www.youtube.com/")),
      // Callback button
      InlineKeyboardButton("Callback", callbackData = Some("Callback")),
      // Switch inline button (current chat)
      InlineKeyboardButton("Inline chat", switchInlineQueryCurrentChat = Some("HI")),
      // Switch inline button (inline query)
      InlineKeyboardButton("Inline query", switchInlineQuery = Some("")),
    ))))
    val message = SendMessage(groupChatId, "Various Inline Buttons", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }

  // Force reply

  test("A message that displays a reply interface to the user can be sent") {
    val keyboard = Some(ForceReply())
    val message = SendMessage(groupChatId, "You all must answer me", replyMarkup = keyboard).call()
    assert(message.isSuccess)
  }
}
