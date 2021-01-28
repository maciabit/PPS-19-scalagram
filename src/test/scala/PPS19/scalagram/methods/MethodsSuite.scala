package PPS19.scalagram.methods

import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite {

  /*test("A message can be sent") {
    Props.load()
    val chatId = "-1001364807173"
    val text = "IntelliJ Test"
    val sm = new SendMessage(chatId = chatId, text = text)
    val r = sm.sendMessage()
    assert(r == 200)
  }*/

  test("A Telegram API call can be performed using TelegramMethod Trait") {
    Props.load()
    val text = GetNewUpdates().getNewUpdates()
    println(text)
    assert(true)
  }
}
