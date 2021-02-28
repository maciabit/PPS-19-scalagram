package PPS19.scalagram.models

import io.circe.jawn.decode
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

import java.nio.file.{Files, Paths}

@RunWith(classOf[JUnitRunner])
class ModelsSuite extends AnyFunSuite {

  private def testUpdateDecoding(expectedJsonPath: String): Unit = {
    val messageString =
      new String(Files.readAllBytes(Paths.get(getClass.getClassLoader.getResource(expectedJsonPath).toURI)))
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
    testUpdateDecoding(s"updates/EditedChannelPostUpdate.json")
  }

  test("A EditedTextMessage update can be decoded") {
    testUpdateDecoding(s"updates/EditedTextMessageUpdate.json")
  }

  test("A MessagePinned update can be decoded") {
    testUpdateDecoding(s"updates/MessagePinnedUpdate.json")
  }

  test("A PhotoMessage update can be decoded") {
    testUpdateDecoding(s"updates/PhotoMessageUpdate.json")
  }

  test("A TextMessage update can be decoded") {
    testUpdateDecoding(s"updates/TextMessageUpdate.json")
  }

}
