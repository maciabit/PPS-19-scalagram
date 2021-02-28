package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Bot, BotToken, Context}
import PPS19.scalagram.models.messages._
import PPS19.scalagram.models._
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReactionsSuite extends AnyFunSuite {
  val bot: Bot = Bot(BotToken(""))
  val chat: Chat = Supergroup(0, None, None)
  val context: Context = Context(bot)

  def testReaction(update: Update, reaction: ReactionBuilder, expectedRes: Boolean = true): Assertion = {
    context.update = Some(update)
    var res = false
    reaction.build(_ => res = true).operation(context)
    assert(res == expectedRes)
  }

  test("An OnMessage reaction that matches any string can be created and used") {
    testReaction(MessageReceived(0, TextMessage(0, chat, 0, "message")), OnMessage())
  }

  test("An OnMessage reaction that matches a single string can be created and used") {
    testReaction(MessageReceived(0, TextMessage(0, chat, 0, "message")), OnMessage("message"))
  }

  test("An OnMessage reaction that matches multiple strings can be created and used") {
    testReaction(MessageReceived(0, TextMessage(0, chat, 0, "message")), OnMessage("message", "another message"))
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "another message")),
      OnMessage("message", "another message")
    )
  }

  test(
    "An OnMessage reaction fails if the message passed as parameter is the wrong type"
  ) {
    testReaction(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")), OnMessage(), expectedRes = false)
  }

  test("An OnStart reaction can be created and used") {
    testReaction(MessageReceived(0, TextMessage(0, chat, 0, "/start")), OnStart())
  }

  test("An OnStart reaction fails if the message passed as parameter is the wrong type") {
    testReaction(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")), OnStart(), expectedRes = false)
  }

  test("An OnHelp reaction can be created and used") {
    testReaction(MessageReceived(0, TextMessage(0, chat, 0, "/help")), OnHelp())
  }

  test("An OnHelp reaction fails if the message passed as parameter is the wrong type") {
    testReaction(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")), OnHelp(), expectedRes = false)
  }

  test("An OnMessageEdited reaction that matches any string can be created and used") {
    testReaction(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")), OnMessageEdited())
  }

  test("An OnMessageEdited reaction that matches a single string can be created and used") {
    testReaction(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")), OnMessageEdited("/messageEdited"))
  }

  test("An OnMessageEdited reaction that matches multiple strings can be created and used") {
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")),
      OnMessageEdited("/messageEdited", "/messageEdited2")
    )
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited2")),
      OnMessageEdited("/messageEdited", "/messageEdited2")
    )
  }

  test("An OnMessageEdited reaction fails if the message passed as parameter is the wrong type") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnMessageEdited("/messageEdited"),
      expectedRes = false
    )
  }

  test("An OnMessagePinned reaction can be created and used") {
    testReaction(
      MessageReceived(0, MessagePinned(0, chat, 0, TextMessage(0, chat, 0, "messagePinned"))),
      OnMessagePinned()
    )
  }

  test("An OnMessagePinned reaction fails if the message passed as parameter is the wrong type") {
    testReaction(MessageReceived(0, TextMessage(0, chat, 0, "message")), OnMessagePinned(), expectedRes = false)
  }

  test("An OnMatch reaction can be created and used") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 1614070068, "https://www.google.com")),
      OnMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      )
    )
  }

  test("An OnMatch reaction fails if the message passed as parameter is the wrong type") {
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited2")),
      OnMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ),
      expectedRes = false
    )
  }

  test("An OnChatLeave reaction can be created and used") {
    testReaction(MessageReceived(0, ChatMemberRemoved(0, chat, 0, User(0, firstName = "Bob"))), OnChatLeave())
  }

  test("An OnChatLeave reaction fails if the message passed as parameter is the wrong type") {
    testReaction(
      MessageReceived(0, ChatMembersAdded(0, chat, 0, Seq(User(0, firstName = "Bob")))),
      OnChatLeave(),
      expectedRes = false
    )
  }

  test("An OnChatEnter reaction can be created and used") {
    testReaction(MessageReceived(0, ChatMembersAdded(0, chat, 0, Seq(User(0, firstName = "Bob")))), OnChatEnter())
  }

  test("An OnChatEnter reaction fails if the message passed as parameter is the wrong type") {
    testReaction(
      MessageReceived(0, ChatMemberRemoved(0, chat, 0, User(0, firstName = "Bob"))),
      OnChatEnter(),
      expectedRes = false
    )
  }

  test("An OnCallbackQuery reaction can be created and used") {
    testReaction(
      CallbackButtonSelected(
        0,
        CallbackQuery("", User(0, firstName = "Bob"), chatInstance = "", data = Some("data"))
      ),
      OnCallbackQuery("data")
    )
  }

  test("An OnCallbackQuery reaction fails if the message passed as parameter is the wrong type") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnCallbackQuery("data"),
      expectedRes = false
    )
  }
}
