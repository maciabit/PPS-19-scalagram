package PPS19.scalagram.models

import PPS19.scalagram.models.payloads._
import PPS19.scalagram.models.updates._
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UpdateTypeSuite extends AnyFunSuite {

  private val humanUser: User = User(0, isBot = false, "John");
  private val botUser: User = User(0, isBot = true, "Doe");
  private val chat: Chat = Supergroup(id = 0)

  test("A CallbackButtonSelected UpdateType can be properly set") {
    val update: CallbackButtonSelected = CallbackButtonSelected(0, CallbackQuery("0", botUser, chatInstance = "0"))
    assert(update.updateType == UpdateType.CallbackSelected && update.chat == UnknownChat)
  }

  test("A Channel Post UpdateType can be properly set") {
    val update: ChannelPostReceived = ChannelPostReceived(0, TextMessage(0, chat, 0, "Message"))
    assert(update.updateType == UpdateType.ChannelPostReceived)
  }

  test("A EditedChannelPost UpdateType can be properly set") {
    val update: ChannelPostEdited = ChannelPostEdited(0, TextMessage(0, chat, 0, "Message"))
    assert(update.updateType == UpdateType.ChannelPostEdited)
  }

  test("A ChatMemberRemoved UpdateType can be properly set") {
    val update: MessageUpdate = MessageReceived(0, ChatMemberRemoved(0, chat, 0, humanUser))
    assert(update.updateType == UpdateType.ChatMemberRemoved)
  }

  test("A ChatMembersAdded UpdateType can be properly set") {
    val update: MessageUpdate = MessageReceived(0, ChatMembersAdded(0, chat, 0, Seq(humanUser)))
    assert(update.updateType == UpdateType.ChatMembersAdded && update.from.isEmpty)
  }

  test("A EditedTextMessage UpdateType can be properly set") {
    val update: MessageEdited = MessageEdited(0, TextMessage(0, chat, 0, "Message"))
    assert(update.updateType == UpdateType.MessageEdited)
  }

  test("A TextMessage UpdateType can be properly set") {
    val update: MessageReceived = MessageReceived(0, TextMessage(0, chat, 0, "Message"))
    assert(update.updateType == UpdateType.MessageReceived)
  }

  test("A MessagePinned UpdateType can be properly set") {
    val update: MessageUpdate = MessageReceived(0, MessagePinned(0, chat, 0, TextMessage(0, chat, 0, "Message")))
    assert(update.updateType == UpdateType.MessagePinned)
  }

  test("A UnknownUpdate UpdateType can be properly set") {
    val update: UnknownUpdate = UnknownUpdate(0)
    assert(update.updateType == UpdateType.Unknown)
  }
}
