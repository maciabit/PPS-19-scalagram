package PPS19.scalagram.models

import PPS19.scalagram.methods.SendMessage
import PPS19.scalagram.models.messages.TextMessage
import PPS19.scalagram.utils.Props
import io.circe.parser.decode
import io.circe.syntax.EncoderOps
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ModelsSuite extends AnyFunSuite{

  test("A message with a Response Keyboard with a single button can be sent"){
    Props.load()
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Button created on intellij")))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Provaaaa[][][]]'''!!!-.-.-[]é*}{}{", replyMarkup = Some(a))
  }

  test("A message with a Response Keyboard with a row of button can be sent"){
    Props.load()
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Button created on intellij"), KeyboardButton(text = "Button created on intellij second")))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Provaaaa[][][]]'''!!!-.-.-[]é*}{}{", replyMarkup = Some(a))
  }

  test("A message with a Response Keyboard with a column of button can be sent"){
    Props.load()
    val a = (ReplyKeyboardMarkup(keyboard = Seq(Seq(KeyboardButton(text = "Button created on intellij second")),Seq(KeyboardButton(text = "Button created on intellij")))) : ReplyMarkup)
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "Provaaaa[][][]]'''!!!-.-.-[]é*}{}{", replyMarkup = Some(a))
  }

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
    println(messageDec)
    assert(messageDec.isRight)
  }
}
