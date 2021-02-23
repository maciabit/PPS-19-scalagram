package PPS19.scalagram.methods

import PPS19.scalagram.logic.{Bot, BotToken}
import PPS19.scalagram.models.{ChatId, RemoteMedia, UploadMedia}
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
  private val chatId = ChatId("-1001286594106")

  override def beforeEach(): Unit = {
    Thread.sleep(3000)
  }

  test("A message can be sent") {
    assert(bot.sendMessage(chatId, "Test message").isSuccess)
  }

  test("A message formatted as HTML can be sent") {
    val res = bot
      .sendMessage(
        chatId,
        "Test message in HTML <b>bold</b> <i>italic</i>",
        Some("HTML")
      )
    if (res.isFailure) println(res)
    assert(res.isSuccess)
  }

  test("A message formatted as Markdown can be sent") {
    val res = bot
      .sendMessage(
        chatId,
        "Test message in Markdown *bold* _italic_ ~strikethrough~",
        Some("MarkdownV2")
      )
    if (res.isFailure) println(res)
    assert(res.isSuccess)
  }

  test("An image can be sent using an URL") {
    val url = RemoteMedia(imageUrl)
    assert(bot.sendPhoto(chatId, url).isSuccess)
  }

  test("An image can be sent using a file") {
    val url = UploadMedia(placeholderPhoto)
    assert(bot.sendPhoto(chatId, url).isSuccess)
  }

  test("Updates can be retrieved") {
    assert(bot.getUpdates().isSuccess)
  }

  test("A message can be deleted") {
    val message = bot.sendMessage(chatId, "Test message to be deleted")
    assert(bot.deleteMessage(chatId, message.get.messageId).isSuccess)
  }

  test("A message can be pinned and unpinned") {
    val firstMessageToPin = bot.sendMessage(chatId, "First message to pin")
    val secondMessageToPin =
      bot.sendMessage(chatId, "Second message to pin")
    val thirdMessageToPin = bot.sendMessage(chatId, "Third message to pin")
    assert(
      bot
        .pinMessage(chatId, firstMessageToPin.get.messageId, Some(true))
        .isSuccess
    )
    assert(
      bot
        .pinMessage(chatId, secondMessageToPin.get.messageId, Some(true))
        .isSuccess
    )
    assert(
      bot
        .pinMessage(chatId, thirdMessageToPin.get.messageId, Some(true))
        .isSuccess
    )
    assert(
      bot.unpinMessage(chatId, firstMessageToPin.get.messageId).isSuccess
    )
  }

  test("All pinned messages of a given chat can be unpinned at once") {
    assert(bot.unpinAllMessages(chatId).isSuccess)
  }
}
