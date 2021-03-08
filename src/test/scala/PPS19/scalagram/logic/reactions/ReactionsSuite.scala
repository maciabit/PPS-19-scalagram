package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Bot, Context, Reaction}
import PPS19.scalagram.models._
import PPS19.scalagram.models.messages._
import org.junit.runner.RunWith
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReactionsSuite extends AnyFunSuite {
  val bot: Bot = Bot(BotToken(""))
  val chat: Chat = Supergroup(0, None, None)
  val context: Context = Context(bot)

  def testReaction(
      update: Update,
      reactionBuilder: ReactionBuilder,
      botReaction: (Context => Unit) => Reaction,
      expectedRes: Boolean = true
  ): Assertion = {
    context.update = Some(update)
    var res = false
    var botRes = false
    reactionBuilder.build(_ => res = true).operation(context)
    botReaction(_ => botRes = true).operation(context)
    assert(res == expectedRes && botRes == expectedRes)
  }

  test("An OnMessage reaction that matches any string works as intended") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnMessage(),
      Bot.onMessage()
    )
  }

  test("An OnMessage reaction that matches a single string works as intended") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnMessage("message"),
      Bot.onMessage("message")
    )
  }

  test("An OnMessage reaction that matches multiple strings works as intended") {
    val onMessage = OnMessage("message", "another message")
    val botOnMessage: (Context => Unit) => Reaction =
      Bot.onMessage("message", "another message")
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      onMessage,
      botOnMessage
    )
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "another message")),
      onMessage,
      botOnMessage
    )
  }

  test("An OnMessage reaction fails if the message passed as parameter is of the wrong type") {
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "edited message")),
      OnMessage(),
      Bot.onMessage(),
      expectedRes = false
    )
  }

  test("An OnStart reaction works as intended") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "/start")),
      OnStart(),
      Bot.onStart
    )
  }

  test("An OnHelp reaction works as intended") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "/help")),
      OnHelp(),
      Bot.onHelp
    )
  }

  test("An OnMessageEdited reaction that matches any string works as intended") {
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "edited message")),
      OnMessageEdited(),
      Bot.onMessageEdited()
    )
  }

  test("An OnMessageEdited reaction that matches a single string works as intended") {
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "edited message")),
      OnMessageEdited("edited message"),
      Bot.onMessageEdited("edited message")
    )
  }

  test("An OnMessageEdited reaction that matches multiple strings works as intended") {
    val onMessageEdited = OnMessageEdited("edited message", "edited message 2")
    val botOnMessageEdited: (Context => Unit) => Reaction =
      Bot.onMessageEdited("edited message", "edited message 2")
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "edited message")),
      onMessageEdited,
      botOnMessageEdited
    )
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "edited message 2")),
      onMessageEdited,
      botOnMessageEdited
    )
  }

  test("An OnMessageEdited reaction fails if the message passed as parameter is of the wrong type") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnMessageEdited("edited message"),
      Bot.onMessageEdited("edited message"),
      expectedRes = false
    )
  }

  test("An OnMessagePinned reaction works as intended") {
    testReaction(
      MessageReceived(0, MessagePinned(0, chat, 0, TextMessage(0, chat, 0, "messagePinned"))),
      OnMessagePinned(),
      Bot.onMessagePinned
    )
  }

  test("An OnMessagePinned reaction fails if the message passed as parameter is of the wrong type") {
    // Same update class, different message class
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnMessagePinned(),
      Bot.onMessagePinned,
      expectedRes = false
    )
    // Different update class
    testReaction(
      CallbackButtonSelected(0, CallbackQuery("", User(0, firstName = "Bob"), chatInstance = "")),
      OnMessagePinned(),
      Bot.onMessagePinned,
      expectedRes = false
    )
  }

  test("An OnMatch reaction works as intended") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "https://www.google.com")),
      OnMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ),
      Bot.onMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      )
    )
  }

  test("An OnMatch reaction fails if the message passed as parameter is of the wrong type") {
    // Same update class, different message class
    testReaction(
      MessageEdited(0, TextMessage(0, chat, 0, "edited message")),
      OnMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ),
      Bot.onMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ),
      expectedRes = false
    )
    // Different update class
    testReaction(
      CallbackButtonSelected(0, CallbackQuery("", User(0, firstName = "Bob"), chatInstance = "")),
      OnMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ),
      Bot.onMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ),
      expectedRes = false
    )
  }

  test("An OnChatEnter reaction works as intended") {
    testReaction(
      MessageReceived(0, ChatMembersAdded(0, chat, 0, Seq(User(0, firstName = "Bob")))),
      OnChatEnter(),
      Bot.onChatEnter
    )
  }

  test("An OnChatEnter reaction fails if the message passed as parameter is of the wrong type") {
    // Same update class, different message class
    testReaction(
      MessageReceived(0, ChatMemberRemoved(0, chat, 0, User(0, firstName = "Bob"))),
      OnChatEnter(),
      Bot.onChatEnter,
      expectedRes = false
    )
    // Different update class
    testReaction(
      CallbackButtonSelected(0, CallbackQuery("", User(0, firstName = "Bob"), chatInstance = "")),
      OnChatEnter(),
      Bot.onChatEnter,
      expectedRes = false
    )
  }

  test("An OnChatLeave reaction works as intended") {
    testReaction(
      MessageReceived(0, ChatMemberRemoved(0, chat, 0, User(0, firstName = "Bob"))),
      OnChatLeave(),
      Bot.onChatLeave
    )
  }

  test("An OnChatLeave reaction fails if the message passed as parameter is of the wrong type") {
    // Same update class, different message class
    testReaction(
      MessageReceived(0, ChatMembersAdded(0, chat, 0, Seq(User(0, firstName = "Bob")))),
      OnChatLeave(),
      Bot.onChatLeave,
      expectedRes = false
    )
    // Different update class
    testReaction(
      CallbackButtonSelected(0, CallbackQuery("", User(0, firstName = "Bob"), chatInstance = "")),
      OnChatLeave(),
      Bot.onChatLeave,
      expectedRes = false
    )
  }

  test("An OnCallbackQuery reaction works as intended") {
    testReaction(
      CallbackButtonSelected(
        0,
        CallbackQuery("", User(0, firstName = "Bob"), chatInstance = "", data = Some("data"))
      ),
      OnCallbackQuery("data"),
      Bot.onCallbackQuery("data")
    )
  }

  test("An OnCallbackQuery reaction fails if the message passed as parameter is of the wrong type") {
    testReaction(
      MessageReceived(0, TextMessage(0, chat, 0, "message")),
      OnCallbackQuery("data"),
      Bot.onCallbackQuery("data"),
      expectedRes = false
    )
  }
}
