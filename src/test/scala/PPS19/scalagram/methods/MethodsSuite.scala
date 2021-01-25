package PPS19.scalagram.methods

import org.scalatest.funsuite.AnyFunSuite

class MethodsSuite extends AnyFunSuite{
  test("Send a message") {
    val peer = InputPeerChat("-1001364807173")
    val text = "IntelliJ Test"
    val sm = new SendMessage(peer, text)
    val r = sm.sendMessage()
    assert(r == 200)
  }
}
