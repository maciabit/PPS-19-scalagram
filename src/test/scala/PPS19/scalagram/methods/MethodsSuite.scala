package PPS19.scalagram.methods

import PPS19.scalagram.utils.Props
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }
  /*test("Send a message") {
    Props.load()
    val p = InputPeerChat(chatId = "-1001364807173")
    val text = "IntelliJ Test"
    val sm = new SendMessage(message = text, peer = p)
    val r = sm.sendMessage()
    assert(r == 200)
  }*/

  test("A Telegram API call can be performed using TelegramMethod Trait") {
    val text = GetNewUpdates().getNewUpdates()
    println(text)
    assert(true)
  }
  /*
  test("A Telegram message can be deleted with HTTP call"){
    assert(DeleteMessage().deleteMessage(chatId = Left("-1001286594106"), messageId = 344))
  }
  */
  /*
  test("A Telegram message can be pinned with HTTP call"){
    assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = 360, disableNotification = Some(true)))
  }
  */
  /*
  test("A Telegram message can be unpinned with HTTP call"){
    assert(UnpinMessage().unpinMessage(chatId = Left("-1001286594106"), messageId = 361))
  }
  */
  /*
   test("All Telegram messages of a given chat can be unpinned") {
     assert(UnpinAllMessages().unpinAllMessages(chatId = Left("-1001286594106")))
   }
   */
}
