package PPS19.scalagram.methods

import org.scalatest.funsuite.AnyFunSuite

class MethodsSuite extends AnyFunSuite{
  test("Send a message") {
    val p = InputPeerChat(chatId = "-1001364807173")
    val text = "IntelliJ Test"
    val sm = new SendMessage(message = text, peer = p)
    val r = sm.sendMessage()
    assert(r == 200)
  }
}
