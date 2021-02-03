package PPS19.scalagram.methods

import PPS19.scalagram.models.{ExistingMedia, RemoteMedia}
import PPS19.scalagram.utils.Props
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter with BeforeAndAfterEach {

  before {
    Props.load()
  }

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  test("A message can be sent") {
    assert(SendMessage().sendMessage(chatId = Left("-1001286594106"), text = "IntelliJ Test").isSuccess)
  }

  test("An image can be sent using direct URL") {
    val url = RemoteMedia("https://cdn.pixabay.com/photo/2017/10/24/00/39/bot-icon-2883144_1280.png")
    assert(SendPhoto().sendPhoto(chatId = Left("-1001286594106"), photo = url).isSuccess)
  }

  /*test("An image can be sent using its id if already exits") {
    val id = ExistingMedia("AgACAgQAAx0ETK_eOgACDX5gGBsCoUnpGj9O7XHDkpjsEYbhQQAC07UxGzzIwVA-L6tqZfWUa9kB-ChdAAMBAAMCAANtAANSPgMAAR4E")
    assert(SendPhoto().sendPhoto(chatId = Left("-1001286594106"), photo = id).isSuccess)
  }*/

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

@RunWith(classOf[JUnitRunner])
class SingleTestForSpam extends AnyFunSuite with BeforeAndAfter {

  before {
    Props.load()
  }
}