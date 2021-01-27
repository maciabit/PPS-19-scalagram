package PPS19.scalagram.methods

import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite {

  /*test("Send a message") {
    Props.load()
    val p = InputPeerChat(chatId = "-1001364807173")
    val text = "IntelliJ Test"
    val sm = new SendMessage(message = text, peer = p)
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
