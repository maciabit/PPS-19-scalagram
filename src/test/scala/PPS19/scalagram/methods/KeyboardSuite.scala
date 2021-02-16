package PPS19.scalagram.methods

import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.models._
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class KeyboardSuite extends AnyFunSuite with BeforeAndAfterEach {

  private val bot = Bot(BotToken(Props.get("token")))
  private val chatId = ChatId("-1001286594106")

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  // Response Keyboard

  test("A message with a single button Response Keyboard can be sent") {
    val keyboard =
      Some(ReplyKeyboardMarkup(Seq(Seq(ReplyKeyboardButton("Button")))))
    val message = bot.sendMessage(
      chatId,
      "Single button keyboard",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }

  test("A message with a column of buttons Response Keyboard can be sent") {
    val keyboard = Some(
      ReplyKeyboardMarkup(
        Seq(
          Seq(ReplyKeyboardButton("Button 1"), ReplyKeyboardButton("Button 2"))
        )
      )
    )
    val message = bot.sendMessage(
      chatId,
      "Row of buttons keyboard",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }

  test("A message with a row of buttons Response Keyboard can be sent") {
    val keyboard = Some(
      ReplyKeyboardMarkup(
        Seq(
          Seq(ReplyKeyboardButton("Button 1")),
          Seq(ReplyKeyboardButton("Button 2"))
        )
      )
    )
    val message = bot.sendMessage(
      chatId,
      "Column of buttons keyboard",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }

  test("A message with a grid of buttons Response Keyboard can be sent") {
    val keyboard = Some(
      ReplyKeyboardMarkup(
        Seq(
          Seq(ReplyKeyboardButton("Button 1"), ReplyKeyboardButton("Button 2")),
          Seq(ReplyKeyboardButton("Button 3"), ReplyKeyboardButton("Button 4"))
        )
      )
    )
    val message = bot.sendMessage(
      chatId,
      "Grid of buttons keyboard",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }

  test("A Response Keyboard can be removed") {
    val keyboard = Some(ReplyKeyboardRemove())
    val message = bot.sendMessage(
      chatId,
      "Removing response keyboard",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }

  // Inline Keyboard

  test(
    "URL Buttons, Callback Buttons and Switch to Inline Buttons can be sent in an Inline Keyboard"
  ) {
    val keyboard = Some(
      InlineKeyboardMarkup(
        Seq(
          Seq(
            InlineKeyboardButton.url("Link", "https://www.youtube.com/"),
            InlineKeyboardButton.callback("Callback", "Callback"),
            InlineKeyboardButton
              .switchInlineQueryCurrentChat("Inline chat", "HI"),
            InlineKeyboardButton.switchInlineQuery("Inline query", "")
          )
        )
      )
    )
    val message = bot.sendMessage(
      chatId,
      "Various Inline Buttons",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }

  // Force reply

  test("A message that displays a reply interface to the user can be sent") {
    val keyboard = Some(ForceReply())
    val message = bot.sendMessage(
      chatId,
      "You all must answer me",
      replyMarkup = keyboard
    )
    assert(message.isSuccess)
  }
}
