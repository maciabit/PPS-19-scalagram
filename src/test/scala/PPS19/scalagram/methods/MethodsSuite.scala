package PPS19.scalagram.methods

import PPS19.scalagram.models.RemoteMedia
import PPS19.scalagram.utils.Props
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter with BeforeAndAfterEach {

  val imageUrl = "https://via.placeholder.com/600/92c952"
  val groupChatId = Left("-1001286594106")

  before {
    Props.load()
  }

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  test("A message can be sent") {
    assert(SendMessage().sendMessage(groupChatId, "Test message").isSuccess)
  }

  test("An image can be sent using an URL") {
    val url = RemoteMedia(imageUrl)
    assert(SendPhoto().sendPhoto(groupChatId, url).isSuccess)
  }

  test("Updates can be retrieved") {
    assert(GetNewUpdates().getNewUpdates().isSuccess)
  }

  test("A message can be deleted") {
    val text = SendMessage().sendMessage(groupChatId, "Test message to be deleted")
    assert(DeleteMessage().deleteMessage(groupChatId, text.get.messageId).isSuccess)
  }

  test("A message can be pinned and unpinned") {
    val firstMessageToPin = SendMessage().sendMessage(groupChatId, "First message to pin")
    val secondMessageToPin = SendMessage().sendMessage(groupChatId, "Second message to pin")
    val thirdMessageToPin = SendMessage().sendMessage(groupChatId, "Third message to pin")
    assert(PinMessage().pinMessage(groupChatId, firstMessageToPin.get.messageId, Some(true)).isSuccess)
    assert(PinMessage().pinMessage(groupChatId, secondMessageToPin.get.messageId, Some(true)).isSuccess)
    assert(PinMessage().pinMessage(groupChatId, thirdMessageToPin.get.messageId, Some(true)).isSuccess)
    assert(UnpinMessage().unpinMessage(groupChatId, firstMessageToPin.get.messageId).isSuccess)
  }

  test("All pinned messages of a given chat can be unpinned at once") {
    assert(UnpinAllMessages().unpinAllMessages(groupChatId).isSuccess)
  }
}