package PPS19.scalagram.methods

import PPS19.scalagram.models.Update
import PPS19.scalagram.models.messages.TelegramMessage
import PPS19.scalagram.utils.{Props, TestUtils}
import io.circe.jawn.decode
import org.scalatest.BeforeAndAfter
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }
  test("A message can be sent") {
    assert(SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "IntelliJ Test").isSuccess)
  }

  test("An image can be sent using direct URL") {
    val url = "https://cdn.pixabay.com/photo/2017/10/24/00/39/bot-icon-2883144_1280.png"
    assert(SendPhoto().sendPhoto(chatId = Left("-1001286594106"), photo = url).isSuccess)
  }

  test("A Telegram API call can be performed using TelegramMethod Trait") {
    assert(GetNewUpdates().getNewUpdates().isSuccess)
  }

  test("A Telegram message can be deleted with HTTP call") {
    val text = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "texttobedeleted")
    assert(DeleteMessage().deleteMessage(chatId = Left("-1001286594106"), messageId = text.get.messageId).isSuccess)
  }

  test("A Telegram message can be pinned and unpinned with HTTP call") {
   val firstMessageToPin = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "firstMessageToPin")
   val secondMessageToPin = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "secondMessageToPin")
   val thirdMessageToPin = SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "thirdMessageToPin")
   assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = firstMessageToPin.get.messageId, disableNotification = Some(true)).isSuccess)
   assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = secondMessageToPin.get.messageId, disableNotification = Some(true)).isSuccess)
   assert(PinMessage().pinMessage(chatId = Left("-1001286594106"), messageId = thirdMessageToPin.get.messageId, disableNotification = Some(true)).isSuccess)
   assert(UnpinMessage().unpinMessage(chatId = Left("-1001286594106"), firstMessageToPin.get.messageId).isSuccess)
  }

   test("All Telegram messages of a given chat can be unpinned") {
     assert(UnpinAllMessages().unpinAllMessages(chatId = Left("-1001286594106")).isSuccess)
   }
}