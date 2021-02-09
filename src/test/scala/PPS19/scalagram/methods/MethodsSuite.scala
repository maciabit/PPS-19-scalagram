package PPS19.scalagram.methods

import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.models.{RemoteMedia, UploadMedia}
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite with BeforeAndAfterEach {

  private val bot = Bot(BotToken(Props.get("token")))
  private val imageUrl = "https://via.placeholder.com/600/92c952"
  private val placeholderPhoto =
    getClass.getClassLoader.getResource("placeholder.jpg").getPath
  private val groupChatId = Left("-1001286594106")

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  test("A message can be sent") {
    assert(bot.sendMessage(groupChatId, "Test message").isSuccess)
  }

  test("An image can be sent using an URL") {
    val url = RemoteMedia(imageUrl)
    assert(bot.sendPhoto(groupChatId, url).isSuccess)
  }

  test("An image can be sent using a file") {
    val url = UploadMedia(placeholderPhoto)
    assert(bot.sendPhoto(groupChatId, url).isSuccess)
  }

  test("Updates can be retrieved") {
    assert(bot.getUpdates().isSuccess)
  }

  test("A message can be deleted") {
    val message = bot.sendMessage(groupChatId, "Test message to be deleted")
    assert(bot.deleteMessage(groupChatId, message.get.messageId).isSuccess)
  }

  test("A message can be pinned and unpinned") {
    val firstMessageToPin = bot.sendMessage(groupChatId, "First message to pin")
    val secondMessageToPin =
      bot.sendMessage(groupChatId, "Second message to pin")
    val thirdMessageToPin = bot.sendMessage(groupChatId, "Third message to pin")
    assert(
      bot
        .pinMessage(groupChatId, firstMessageToPin.get.messageId, Some(true))
        .isSuccess
    )
    assert(
      bot
        .pinMessage(groupChatId, secondMessageToPin.get.messageId, Some(true))
        .isSuccess
    )
    assert(
      bot
        .pinMessage(groupChatId, thirdMessageToPin.get.messageId, Some(true))
        .isSuccess
    )
    assert(
      bot.unpinMessage(groupChatId, firstMessageToPin.get.messageId).isSuccess
    )
  }

  test("All pinned messages of a given chat can be unpinned at once") {
    assert(bot.unpinAllMessages(groupChatId).isSuccess)
  }
}
