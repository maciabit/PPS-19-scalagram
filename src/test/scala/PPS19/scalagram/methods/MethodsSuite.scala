package PPS19.scalagram.methods

import PPS19.scalagram.models.{RemoteMedia, UploadMedia}
import PPS19.scalagram.utils.Props
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfter with BeforeAndAfterEach {

  private val imageUrl = "https://via.placeholder.com/600/92c952"
  private val placeholderPhoto = getClass.getClassLoader.getResource("placeholder.jpg").getPath
  private val groupChatId = Left("-1001286594106")

  before {
    Props.load()
  }

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  test("A message can be sent") {
    assert(SendMessage(groupChatId, "Test message").call().isSuccess)
  }

  test("An image can be sent using an URL") {
    val url = RemoteMedia(imageUrl)
    assert(SendPhoto(groupChatId, url).call().isSuccess)
  }

  test("An image can be sent using a file") {
    val url = UploadMedia(placeholderPhoto)
    assert(SendPhoto(groupChatId, url).call().isSuccess)
  }

  test("Updates can be retrieved") {
    assert(GetNewUpdates().call().isSuccess)
  }

  test("A message can be deleted") {
    val text = SendMessage(groupChatId, "Test message to be deleted").call()
    assert(DeleteMessage(groupChatId, text.get.messageId).call().isSuccess)
  }

  test("A message can be pinned and unpinned") {
    val firstMessageToPin = SendMessage(groupChatId, "First message to pin").call()
    val secondMessageToPin = SendMessage(groupChatId, "Second message to pin").call()
    val thirdMessageToPin = SendMessage(groupChatId, "Third message to pin").call()
    assert(PinMessage(groupChatId, firstMessageToPin.get.messageId, Some(true)).call().isSuccess)
    assert(PinMessage(groupChatId, secondMessageToPin.get.messageId, Some(true)).call().isSuccess)
    assert(PinMessage(groupChatId, thirdMessageToPin.get.messageId, Some(true)).call().isSuccess)
    assert(UnpinMessage(groupChatId, firstMessageToPin.get.messageId).call().isSuccess)
  }

  test("All pinned messages of a given chat can be unpinned at once") {
    assert(UnpinAllMessages(groupChatId).call().isSuccess)
  }
}
