package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.models.{ChatId, RemoteMedia, UploadMedia}
import org.junit.runner.RunWith
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import requests.MultiItem

import java.io.File

@RunWith(classOf[JUnitRunner])
class MethodsSuite extends AnyFunSuite {

  private val token = BotToken("<TOKEN>")
  private val baseUrl = s"https://api.telegram.org/bot${token.get}/"
  private val chatId = ChatId(0)

  private def testTelegramRequest[T](
      request: TelegramRequest[T],
      endpoint: String,
      expectedUrlParams: Map[String, String]
  ): Assertion = {
    assert(request.endpointUrl == baseUrl + endpoint)
    assert(request.computedUrlParams == expectedUrlParams)
  }

  test("A sendMessage request can be properly created") {
    testTelegramRequest(
      SendMessage(token, chatId, "message", Some("HTML")),
      "sendMessage",
      Map("chat_id" -> chatId.get.toString, "text" -> "message", "parse_mode" -> "HTML")
    )
  }

  test("A message can be edited") {
    testTelegramRequest(
      EditMessage(token, chatId, "message", Some(0), Some("0"), Some("HTML")),
      "editMessageText",
      Map(
        "chat_id" -> chatId.get.toString,
        "message_idf" -> "0",
        "text" -> "message",
        "parse_mode" -> "HTML",
        "inline_message_id" -> "0"
      )
    )
  }

  test("A sendPhoto request for a remote image can be properly created") {
    testTelegramRequest(
      SendPhoto(token, chatId, RemoteMedia("<IMAGE_URL>"), Some("caption")),
      "sendPhoto",
      Map("chat_id" -> chatId.get.toString, "photo" -> "<IMAGE_URL>", "caption" -> "caption")
    )
  }

  test("A sendPhoto request for a local image can be properly created") {
    val placeholderPhoto = getClass.getClassLoader.getResource("placeholder.jpg").getPath
    val photoFile = new File(placeholderPhoto)
    val sendPhoto = SendPhoto(token, chatId, UploadMedia(placeholderPhoto), Some("caption"))
    testTelegramRequest(
      sendPhoto,
      "sendPhoto",
      Map("chat_id" -> chatId.get.toString, "caption" -> "caption")
    )
    val expectedMultiItem = MultiItem("photo", photoFile, photoFile.getName)
    assert(sendPhoto.computedMultipartFormData.head.name == expectedMultiItem.name)
    assert(sendPhoto.computedMultipartFormData.head.filename == expectedMultiItem.filename)
  }

  test("An answerCallbackQuery request can be properly created") {
    testTelegramRequest(
      AnswerCallbackQuery(token, "<CALLBACK_ID>", Some("message"), Some(false), Some("url"), Some(0)),
      "answerCallbackQuery",
      Map(
        "callback_query_id" -> "<CALLBACK_ID>",
        "text" -> "message",
        "show_alert" -> "false",
        "url" -> "url",
        "cache_time" -> "0"
      )
    )
  }

  test("A getUpdates request can be properly created") {
    testTelegramRequest(
      GetUpdates(token, Some(1), Some(2), Some(3)),
      "getUpdates",
      Map("offset" -> "1", "limit" -> "2", "timeout" -> "3")
    )
  }

  test("A deleteMessage request can be properly created") {
    testTelegramRequest(
      DeleteMessage(token, chatId, 0),
      "deleteMessage",
      Map("chat_id" -> chatId.get.toString, "message_id" -> "0")
    )
  }

  test("A pinMessage request can be properly created") {
    testTelegramRequest(
      PinMessage(token, chatId, 0, Some(false)),
      "pinChatMessage",
      Map("chat_id" -> chatId.get.toString, "message_id" -> "0", "disable_notification" -> "false")
    )
  }

  test("A unpinMessage request can be properly created") {
    testTelegramRequest(
      UnpinMessage(token, chatId, 0),
      "unpinChatMessage",
      Map("chat_id" -> chatId.get.toString, "message_id" -> "0")
    )
  }

  test("A unpinAllMessages request can be properly created") {
    testTelegramRequest(
      UnpinAllMessages(token, chatId),
      "unpinAllChatMessages",
      Map("chat_id" -> chatId.get.toString)
    )
  }
}
