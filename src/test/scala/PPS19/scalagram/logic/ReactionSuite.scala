package PPS19.scalagram.logic

import PPS19.scalagram.logic.reactions.{
  OnCallbackQuery,
  OnChatEnter,
  OnChatLeave,
  OnHelp,
  OnMatch,
  OnMessage,
  OnMessageEdited,
  OnMessagePinned,
  OnStart
}
import PPS19.scalagram.models.{
  BotUser,
  CallbackButtonSelected,
  Chat,
  HumanUser,
  MessageEdited,
  MessageReceived,
  Supergroup
}
import PPS19.scalagram.models.messages.{CallbackQuery, ChatMemberRemoved, ChatMembersAdded, MessagePinned, TextMessage}
import PPS19.scalagram.utils.Props
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReactionSuite extends AnyFunSuite {
  val bot: Bot = Bot(BotToken(Props.get("token")))
  val chat: Chat = Supergroup(-1001286594106L, None, None)

  test("An OnMessage reaction with a single trigger can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        TextMessage(messageId = 16032, chat = chat, date = 1614070068, text = "/uno")
      )
    )
    assert(OnMessage("/uno").build(_ => { "" }).trigger.matches(context))
  }

  test("An OnMessage reaction with multiple triggers can be created and used") {
    val context: Context = Context(bot)
    val secondContext: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        TextMessage(messageId = 16032, chat = chat, date = 1614070068, text = "/uno")
      )
    )
    secondContext.update = Some(
      MessageReceived(
        updateId = 786604279,
        TextMessage(messageId = 16032, chat = chat, date = 1614070068, text = "/1")
      )
    )
    assert(
      OnMessage("/uno", "/1").build(_ => { "" }).trigger.matches(context) && OnMessage("/uno", "/1")
        .build(_ => { "" })
        .trigger
        .matches(secondContext)
    )
  }

  test("An OnStart reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        TextMessage(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          text = "/start"
        )
      )
    )
    assert(OnStart().build(_ => { "" }).trigger.matches(context))
  }

  test("An OnMessageEdited reaction with a single trigger can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageEdited(
        updateId = 786604279,
        TextMessage(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          text = "/messageEdited"
        )
      )
    )
    assert(OnMessageEdited("/messageEdited").build(_ => { "" }).trigger.matches(context))
  }

  test("An OnMessageEdited reaction with multiple triggers can be created and used") {
    val context: Context = Context(bot)
    val secondContext: Context = Context(bot)
    context.update = Some(
      MessageEdited(
        updateId = 786604279,
        TextMessage(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          text = "/messageEdited"
        )
      )
    )
    context.update = Some(
      MessageEdited(
        updateId = 786604279,
        TextMessage(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          text = "/messageEdited2"
        )
      )
    )
    assert(
      OnMessageEdited("/messageEdited", "/messageEdited2")
        .build(_ => { "" })
        .trigger
        .matches(context) && OnMessageEdited("/messageEdited", "/messageEdited2")
        .build(_ => { "" })
        .trigger
        .matches(context)
    )
  }

  test("An OnMessagePinned reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        MessagePinned(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          pinnedMessage = TextMessage(
            messageId = 16031,
            chat = chat,
            date = 1614070068,
            text = "messagePinned"
          )
        )
      )
    )
    assert(OnMessagePinned().build(_ => { "" }).trigger.matches(context))
  }

  test("An OnMatch reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        TextMessage(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          text = "https://www.google.com"
        )
      )
    )
    assert(
      OnMatch(
        "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$"
      ).build(_ => { "" }).trigger.matches(context)
    )
  }

  test("An OnHelp reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        TextMessage(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          text = "/help"
        )
      )
    )
    assert(
      OnHelp().build(_ => { "" }).trigger.matches(context)
    )
  }

  test("An OnChatLeave reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        ChatMemberRemoved(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          leftChatMember = BotUser(id = 701303422, firstName = "MTTOfferte")
        )
      )
    )
    assert(
      OnChatLeave().build(_ => { "" }).trigger.matches(context)
    )
  }

  test("An OnChatEnter reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      MessageReceived(
        updateId = 786604279,
        ChatMembersAdded(
          messageId = 16032,
          chat = chat,
          date = 1614070068,
          newChatMembers = Seq(BotUser(id = 701303422, firstName = "MTTOfferte"))
        )
      )
    )
    assert(
      OnChatEnter().build(_ => { "" }).trigger.matches(context)
    )
  }

  //CallbackButtonSelected(786604290,CallbackQuery(1133402396105403315,HumanUser(263890809,false,Francesco,Some(Boschi),Some(Flavietto4),Some(it),None,None,None),Some(TextMessage(16051,Supergroup(-1001286594106,Some(Scala&NodeBots),None),1614077401,Inline keyboard,None,Some(HumanUser(1192646126,true,NodeJsProva,None,Some(NodeJsProvaBot),None,None,None,None)),None,None,None,None,None,None,None,None,None,None)),None,-2042390633247736041,Some(data),None))
  test("An OnCallbackQuery reaction can be created and used") {
    val context: Context = Context(bot)
    context.update = Some(
      CallbackButtonSelected(
        updateId = 786604279,
        CallbackQuery(
          id = "1133402396105403315",
          from = HumanUser(id = 263890809, firstName = "Francesco"),
          chatInstance = "-2042390633247736041",
          data = Some("data")
        )
      )
    )
    assert(
      OnCallbackQuery("data").build(_ => { "" }).trigger.matches(context)
    )
  }

}
