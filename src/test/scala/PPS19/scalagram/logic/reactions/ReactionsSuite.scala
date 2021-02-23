package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Bot, BotToken, Context}
import PPS19.scalagram.models.messages._
import PPS19.scalagram.models._
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReactionsSuite extends AnyFunSuite {
  val bot: Bot = Bot(BotToken(Props.get("token")))
  val chat: Chat = Supergroup(-1001286594106L, None, None)
  val context: Context = Context(bot)

  test("An OnMessage reaction that matches any string can be created and used") {
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 0, "message")))
    var res = false
    OnMessage().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnMessage reaction that matches a single string can be created and used") {
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 0, "message")))
    var res = false
    OnMessage("message").build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnMessage reaction that matches multiple strings can be created and used") {
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 0, "message")))
    var res1 = false
    OnMessage("message", "another message").build(_ => res1 = true).operation(context)
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 0, "another message")))
    var res2 = false
    OnMessage("message", "another message").build(_ => res2 = true).operation(context)
    assert(res1 && res2)
  }

  test("An OnStart reaction can be created and used") {
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 0, "/start")))
    var res = false
    OnStart().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnHelp reaction can be created and used") {
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 0, "/help")))
    var res = false
    OnHelp().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnMessageEdited reaction that matches any string can be created and used") {
    context.update = Some(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")))
    var res = false
    OnMessageEdited().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnMessageEdited reaction that matches a single string can be created and used") {
    context.update = Some(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")))
    var res = false
    OnMessageEdited("/messageEdited").build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnMessageEdited reaction that matches multiple strings can be created and used") {
    context.update = Some(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited")))
    var res1 = false
    OnMessageEdited("/messageEdited", "/messageEdited2").build(_ => res1 = true).operation(context)
    context.update = Some(MessageEdited(0, TextMessage(0, chat, 0, "/messageEdited2")))
    var res2 = false
    OnMessageEdited("/messageEdited", "/messageEdited2").build(_ => res2 = true).operation(context)
    assert(res1 && res2)
  }

  test("An OnMessagePinned reaction can be created and used") {
    context.update = Some(MessageReceived(0, MessagePinned(0, chat, 0, TextMessage(0, chat, 0, "messagePinned"))))
    var res = false
    OnMessagePinned().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnMatch reaction can be created and used") {
    context.update = Some(MessageReceived(0, TextMessage(0, chat, 1614070068, "https://www.google.com")))
    var res = false
    OnMatch(
      "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
    ).build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnChatLeave reaction can be created and used") {
    context.update = Some(MessageReceived(0, ChatMemberRemoved(0, chat, 0, HumanUser(0, firstName = "Bob"))))
    var res = false
    OnChatLeave().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnChatEnter reaction can be created and used") {
    context.update = Some(MessageReceived(0, ChatMembersAdded(0, chat, 0, Seq(HumanUser(0, firstName = "Bob")))))
    var res = false
    OnChatEnter().build(_ => res = true).operation(context)
    assert(res)
  }

  test("An OnCallbackQuery reaction can be created and used") {
    context.update = Some(
      CallbackButtonSelected(
        0,
        CallbackQuery("", HumanUser(0, firstName = "Bob"), chatInstance = "", data = Some("data"))
      )
    )
    var res = false
    OnCallbackQuery("data").build(_ => res = true).operation(context)
    assert(res)
  }
}
