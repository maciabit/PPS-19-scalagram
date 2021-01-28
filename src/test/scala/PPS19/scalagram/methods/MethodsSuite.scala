package PPS19.scalagram.methods

import PPS19.scalagram.utils.Props
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

import scala.util.Success

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }
  test("Send a message") {
    SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "IntelliJ Test")
    assert(true)
  }

  test("A Telegram API call can be performed using TelegramMethod Trait") {
    val text = GetNewUpdates().getNewUpdates()
    println(text)
    assert(true)
  }

  test("A Telegram message can be deleted with HTTP call"){
    val text = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "texttobedeleted")
    assert(DeleteMessage().deleteMessage(chatId = Left("-1001286594106"), messageId = text.get.messageId))
  }


  test("A Telegram message can be pinned with HTTP call"){
    assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = 353, disableNotification = Some(true)))
    assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = 355, disableNotification = Some(true)))
    assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = 356, disableNotification = Some(true)))
  }

  test("A Telegram message can be unpinned with HTTP call"){
    assert(UnpinMessage().unpinMessage(chatId = Left("-1001286594106"), messageId = 353))
  }

   test("All Telegram messages of a given chat can be unpinned") {
     assert(UnpinAllMessages().unpinAllMessages(chatId = Left("-1001286594106")))
   }
}
