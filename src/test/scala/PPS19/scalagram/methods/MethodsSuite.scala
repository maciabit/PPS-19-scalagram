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
    assert(SendMessage().call(groupChatId, "Test message").isSuccess)
  }

  test("An image can be sent using an URL") {
    val url = RemoteMedia(imageUrl)
    assert(SendPhoto().call(groupChatId, url).isSuccess)
  }

  test("An image can be sent using a file") {
    println(placeholderPhoto)
    val url = UploadMedia(placeholderPhoto)
    assert(SendPhoto().call(groupChatId, url).isSuccess)
  }

  test("Updates can be retrieved") {
    assert(GetNewUpdates().call().isSuccess)
  }

  test("A message can be deleted") {
    val text = SendMessage().call(groupChatId, "Test message to be deleted")
    assert(DeleteMessage().call(groupChatId, text.get.messageId).isSuccess)
  }

  test("A message can be pinned and unpinned") {
    val firstMessageToPin = SendMessage().call(groupChatId, "First message to pin")
    val secondMessageToPin = SendMessage().call(groupChatId, "Second message to pin")
    val thirdMessageToPin = SendMessage().call(groupChatId, "Third message to pin")
    assert(PinMessage().call(groupChatId, firstMessageToPin.get.messageId, Some(true)).isSuccess)
    assert(PinMessage().call(groupChatId, secondMessageToPin.get.messageId, Some(true)).isSuccess)
    assert(PinMessage().call(groupChatId, thirdMessageToPin.get.messageId, Some(true)).isSuccess)
    assert(UnpinMessage().call(groupChatId, firstMessageToPin.get.messageId).isSuccess)
  }

  test("All pinned messages of a given chat can be unpinned at once") {
    assert(UnpinAllMessages().call(groupChatId).isSuccess)
  }
}
