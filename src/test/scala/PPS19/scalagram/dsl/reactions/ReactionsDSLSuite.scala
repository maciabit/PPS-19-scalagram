package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.dsl._
import PPS19.scalagram.logic.Context
import PPS19.scalagram.logic.reactions._
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ReactionsDSLSuite extends AnyFunSuite {

  test("An OnStart reaction created with the DSL equals one created without it") {
    val f: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(!!.>>(f))
    }
    val reaction = OnStart().build(f)
    assert(TestDSL._reactions.head.action == reaction.action)
  }

  test("OnMessage reactions created with the DSL equal ones created without it") {
    val f1: Context => Unit = _ => {}
    val f2: Context => Unit = _ => {}
    val f3: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(
        !!.>>(f1)
          .<<(*)
          .>>(f1)
          .<<("")
          .>>(f2)
          .<<("" | "" | "")
          .>>(f3)
      )
    }
    val onAnyMessage = OnMessage().build(f1)
    val onMessage = OnMessage("").build(f2)
    val onMessages = OnMessage("", "", "").build(f3)
    assert(TestDSL._reactions(1).action == onAnyMessage.action)
    assert(TestDSL._reactions(2).action == onMessage.action)
    assert(TestDSL._reactions(3).action == onMessages.action)
  }

  test("OnMessageEdited reactions created with the DSL equal ones created without it") {
    val f1: Context => Unit = _ => {}
    val f2: Context => Unit = _ => {}
    val f3: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(
        !!.>>(f1)
          .<*(*)
          .>>(f1)
          .<*("")
          .>>(f2)
          .<*("" | "" | "")
          .>>(f3)
      )
    }
    val onAnyMessageEdited = OnMessageEdited().build(f1)
    val onMessageEdited = OnMessageEdited("").build(f2)
    val onMessagesEdited = OnMessageEdited("", "", "").build(f3)
    assert(TestDSL._reactions(1).action == onAnyMessageEdited.action)
    assert(TestDSL._reactions(2).action == onMessageEdited.action)
    assert(TestDSL._reactions(3).action == onMessagesEdited.action)
  }

  test("An OnCallback reaction created with the DSL equals one created without it") {
    val f: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(!!.>>(f).<~("").>>(f))
    }
    val reaction = OnCallbackQuery("").build(f)
    assert(TestDSL._reactions(1).action == reaction.action)
  }

  test("An OnMessagePinned reaction created with the DSL equals one created without it") {
    val f: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(!!.>>(f).<^(*).>>(f))
    }
    val reaction = OnMessagePinned().build(f)
    assert(TestDSL._reactions(1).action == reaction.action)
  }

  test("An OnChatEnter reaction created with the DSL equals one created without it") {
    val f: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(!!.>>(f).<+(*).>>(f))
    }
    val reaction = OnChatEnter().build(f)
    assert(TestDSL._reactions(1).action == reaction.action)
  }

  test("An OnChatLeave reaction created with the DSL equals one created without it") {
    val f: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(!!.>>(f).</(*).>>(f))
    }
    val reaction = OnChatLeave().build(f)
    assert(TestDSL._reactions(1).action == reaction.action)
  }

  test("An OnMatch reaction created with the DSL equals one created without it") {
    val f: Context => Unit = _ => {}
    object TestDSL extends TelegramBotDSL {
      reactions(!!.>>(f).<#("").>>(f))
    }
    val reaction = OnMatch("").build(f)
    assert(TestDSL._reactions(1).action == reaction.action)
  }

  test("A message container created with the DSL with a concatenated Reply Keyboard equals one created without") {
    val dslMessageContainer = "Message text" - Keyboard("Button")
    val messageContainer = MessageContainer("Message text", None, Some(Left(Keyboard("Button"))))
    assert(dslMessageContainer == messageContainer)
  }

  test("A message container created with the DSL with a concatenated Inline Keyboard equals one created without") {
    val dslMessageContainer = "Message text" - InlineKeyboard("Button")
    val messageContainer = MessageContainer("Message text", None, Some(Right(InlineKeyboard("Button"))))
    assert(dslMessageContainer == messageContainer)
  }

  test("An HTML message container created with the DSL equals one created without") {
    val dslMessageContainer = HTML("Message text")
    val messageContainer = MessageContainer("Message text", Some("HTML"), None)
    assert(dslMessageContainer == messageContainer)
  }

  test("A Markdown message container created with the DSL equals one created without") {
    val dslMessageContainer = Markdown("Message text")
    val messageContainer = MessageContainer("Message text", Some("Markdown"), None)
    assert(dslMessageContainer == messageContainer)
  }

  test("A MarkdownV2 message container created with the DSL equals one created without") {
    val dslMessageContainer = MarkdownV2("Message text")
    val messageContainer = MessageContainer("Message text", Some("MarkdownV2"), None)
    assert(dslMessageContainer == messageContainer)
  }

}
