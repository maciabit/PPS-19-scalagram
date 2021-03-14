package PPS19.scalagram.models

import PPS19.scalagram.TestingUtils.getJsonString
import PPS19.scalagram.models.payloads.{CallbackQuery, TextMessage}
import PPS19.scalagram.models.updates.{CallbackButtonSelected, UnknownUpdate, Update, UpdateType}
import io.circe.jawn.decode
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ModelsSuite extends AnyFunSuite {

  private def testUpdateDecoding(expectedJsonPath: String): Unit = {
    val messageString = getJsonString(expectedJsonPath)
    val decodedMessage = decode[Update](messageString)
    assert(decodedMessage.isRight && decodedMessage.getOrElse(null).getClass != UnknownUpdate.getClass)
  }

  test("A CallbackButtonSelected update can be decoded") {
    testUpdateDecoding(s"updates/CallbackButtonSelectedUpdate.json")
  }

  test("A Channel Post update can be decoded") {
    testUpdateDecoding(s"updates/ChannelPostUpdate.json")
  }

  test("A ChatMemberRemoved update can be decoded") {
    testUpdateDecoding(s"updates/ChatMemberRemovedUpdate.json")
  }

  test("A ChatMembersAdded update can be decoded") {
    testUpdateDecoding(s"updates/ChatMembersAddedUpdate.json")
  }

  test("A EditedChannelPost update can be decoded") {
    testUpdateDecoding(s"updates/ChannelPostEditedUpdate.json")
  }

  test("A EditedTextMessage update can be decoded") {
    testUpdateDecoding(s"updates/MessageEditedUpdate.json")
  }

  test("A MessagePinned update can be decoded") {
    testUpdateDecoding(s"updates/MessagePinnedUpdate.json")
  }

  test("A PhotoMessage update can be decoded") {
    testUpdateDecoding(s"updates/PhotoMessageUpdate.json")
  }

  test("A TextMessage update can be decoded") {
    testUpdateDecoding(s"updates/MessageReceivedUpdate.json")
  }

  test("A BotUser update can be decoded") {
    val userString = getJsonString(s"users/BotUser.json")
    val decodedUser = decode[User](userString)
    assert(decodedUser.isRight && decodedUser.getOrElse(null).isBot)
  }

  test("A HumanUser update can be decoded") {
    val userString = getJsonString(s"users/HumanUser.json")
    val decodedUser = decode[User](userString)
    assert(decodedUser.isRight && !decodedUser.getOrElse(null).isBot)
  }

  test("A ChatId containing a String can be decoded") {
    val chatId: ChatId = ChatId("@JohnDoe")
    assert(chatId.get.isInstanceOf[String])
  }

  test("A CallbackButtonSelected update can be created") {
    val update: CallbackButtonSelected = CallbackButtonSelected(
      0,
      CallbackQuery(
        "0",
        User(0, isBot = false, "John"),
        chatInstance = "0",
        message = Some(TextMessage(0, Supergroup(id = 0), 0, "Message"))
      )
    )
    assert(update.updateType == UpdateType.CallbackSelected && update.chat.isInstanceOf[Supergroup])
  }

}
