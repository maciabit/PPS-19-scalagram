package PPS19.scalagram.methods

import PPS19.scalagram.utils.{Props, TestUtils}
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }
//  test("Send a message") {
//    assert(TestUtils.retry(SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "IntelliJ Test"))(3).isSuccess)
//  }

  test("A Telegram API call can be performed using TelegramMethod Trait") {
    GetNewUpdates().getNewUpdates()
    assert(true)
  }

//  test("A Telegram message can be deleted with HTTP call") {
//    val text = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "texttobedeleted")
//    assert(TestUtils.retry(DeleteMessage().deleteMessage(chatId = Left("-1001286594106"), messageId = text.get.messageId))(3).isSuccess)
//  }
//
//  test("A Telegram message can be pinned and unpinned with HTTP call") {
//    val firstMessageToPin = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "firstMessageToPin")
//    val secondMessageToPin = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "secondMessageToPin")
//    val thirdMessageToPin = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "thirdMessageToPin")
//    assert(TestUtils.retry(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = firstMessageToPin.get.messageId, disableNotification = Some(true)))(3).isSuccess)
//    assert(TestUtils.retry(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = secondMessageToPin.get.messageId, disableNotification = Some(true)))(3).isSuccess)
//    assert(TestUtils.retry(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = thirdMessageToPin.get.messageId, disableNotification = Some(true)))(3).isSuccess)
//    assert(TestUtils.retry(UnpinMessage().unpinMessage(chatId = Left("-1001286594106"), firstMessageToPin.get.messageId))(3).isSuccess)
//  }
/*
   test("All Telegram messages of a given chat can be unpinned") {
     assert(TestUtils.retry(UnpinAllMessages().unpinAllMessages(chatId = Left("-1001286594106")))(3).isSuccess)
   }
 */
}
