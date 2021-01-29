package PPS19.scalagram.models

import PPS19.scalagram.methods.SendMessage
import PPS19.scalagram.utils.Props
import io.circe.parser.decode
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ModelsSuite extends AnyFunSuite {

  test("ChannelPostTest"){
    val message = """{
                                "update_id": 971212771,
                                "channel_post": {
                                    "message_id": 344544,
                                    "author_signature": "ProphecyOfferte",
                                    "chat": {
                                        "id": -1001364807173,
                                        "title": "Bot Log",
                                        "type": "channel"
                                    },
                                    "date": 1611486001,
                                    "text": "Alive at 2021-01-24 12:00:00"
                                }
                            }"""
    val messageDec = decode[Update](message)
    assert(messageDec.isRight)
  }
  test("A system Message received in a group can be decoded"){
    val message = """{
            "update_id": 844881872,
            "message": {
                "message_id": 515,
                "from": {
                    "id": 1543598092,
                    "is_bot": true,
                    "first_name": "ScalagramTest",
                    "username": "ScalagramTestBot"
                },
                "chat": {
                    "id": -1001286594106,
                    "title": "NodeBots",
                    "type": "supergroup"
                },
                "date": 1611909473,
                "pinned_message": {
                    "message_id": 510,
                    "from": {
                        "id": 263890809,
                        "is_bot": false,
                        "first_name": "Francesco",
                        "last_name": "Boschi",
                        "username": "Flavietto4"
                    },
                    "chat": {
                        "id": -1001286594106,
                        "title": "NodeBots",
                        "type": "supergroup"
                    },
                    "date": 1611909398,
                    "text": "Leggi"
                }
            }
        }"""
    val messageDec = decode[Update](message)
    assert(messageDec.isRight)
  }
}

@RunWith(classOf[JUnitRunner])
class KeyboardSuite extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }

  /*Response Keyboard test*/
  test("A message with a Response Keyboard with a single button can be sent"){
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Button created on intellij")))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Single button test", replyMarkup = Some(a))
  }

  test("A message with a Response Keyboard with a row of button can be sent"){
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Button created on intellij"), KeyboardButton(text = "Button created on intellij second")))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Row of button test", replyMarkup = Some(a))
  }

  test("A message with a Response Keyboard with a column of button can be sent"){
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Button created on intellij")),Seq(KeyboardButton(text = "Button created on intellij")))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Column of button test", replyMarkup = Some(a))
  }

  /*Response keyboard in private chat for optional feature test*/
  test("A message with a response keyboard allowing user to send his location and phone number can be sent (ONLY FOR PRIVATE CHAT)"){
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Send your phone number", request_contact = Some(true))),Seq(KeyboardButton(text = "Send your location", request_location = Some(true))))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("263890809"), text = "Column of button test", replyMarkup = Some(a))
  }

  /*Inline keyboard test*/
  test("A message with a Inline Keyboard with a single url button can be sent"){
    val a = (InlineKeyboardMarkup(inline_keyboard = Seq(Seq(InlineKeyboardButton(text = "Open youtube", url = Some("https://www.youtube.com/"))))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Single button test", replyMarkup = Some(a))
  }

  test("A message with a Inline Keyboard with a row containing a url button and a callback button can be sent"){
    val a = (InlineKeyboardMarkup(inline_keyboard = Seq(Seq(InlineKeyboardButton(text = "Open youtube", url = Some("https://www.youtube.com/")), InlineKeyboardButton(text = "Callback", callback_data = Some("Callback"))))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Row of button test", replyMarkup = Some(a))
  }

  test("A message with a Inline Keyboard with a column containing a switch_inline button and a switch_inline_current_char button can be sent"){
    val a = (InlineKeyboardMarkup(inline_keyboard = Seq(Seq(InlineKeyboardButton(text = """Write my name followed by "HI"""""", switch_inline_query_current_chat = Some("HI"))), Seq(InlineKeyboardButton(text = "Forward my name in other chats!", switch_inline_query = Some(""))))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Column of button test", replyMarkup = Some(a))
  }

  test("A message with a Inline Keyboard with a single button with parameter to Login and authorize the user can be sent"){
    val a = (InlineKeyboardMarkup(inline_keyboard = Seq(Seq(InlineKeyboardButton(text = "Authorize", login_url = Some(LoginUrl(url = s"https://comments.app/}", botUsername = Some("@discussbot"), forwardText = Some("Donno"),requestWriteAccess = Some(true))))))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Single button test", replyMarkup = Some(a))
  }

  test("A message with a Inline Keyboard with a single button without parameter to Login and authorize the user can be sent"){
    val a = (InlineKeyboardMarkup(inline_keyboard = Seq(Seq(InlineKeyboardButton(text = "Authorize", login_url = Some(LoginUrl(url = s"https://comments.app/}")))))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Single button test", replyMarkup = Some(a))
  }

  /*Remove response keyboard*/
  test("The Response keyboard can be removed"){
    val a = (ReplyKeyboardRemove() : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Removing response keyboard", replyMarkup = Some(a))
  }

  /*ForceReplyTest*/
  test("A message that display a reply interface to the user can be sent"){
    val a = (ForceReply() : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "You all must answer me", replyMarkup = Some(a))
  }
}

@RunWith(classOf[JUnitRunner])
class SingleTestForSpam extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }

}

