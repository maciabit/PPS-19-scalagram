package PPS19.scalagram.methods

import PPS19.scalagram.TestingUtils
import PPS19.scalagram.models.{BotToken, ChatId, RemoteMedia}
import io.circe.Json
import io.circe.parser.parse
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

import scala.util.Success

@RunWith(classOf[JUnitRunner])
class MethodResponseParsingSuite extends AnyFunSuite {

  private val token = BotToken("<TOKEN>")
  private val chatId = ChatId(0)

  test("A sendMessage request can parse the corresponding response") {
    val request = SendMessage(token, chatId, "message", Some("HTML"))
    val response = TestingUtils.getJsonString("messages/SendMessageResponse.json")
    // Test parsing failure
    assert(request.parseSuccessfulResponse(Json.Null).isFailure)
    // Test parsing success
    assert(request.parseSuccessfulResponse(parse(response).getOrElse(Json.Null)).isSuccess)
  }

  test("An editMessage request can parse the corresponding response") {
    val request = EditMessage(token, chatId, "message", Some(0), Some("0"), Some("HTML"))
    val response = TestingUtils.getJsonString("messages/SendMessageResponse.json")
    // Test parsing failure
    assert(request.parseSuccessfulResponse(Json.Null).isFailure)
    // Test parsing success
    assert(request.parseSuccessfulResponse(parse(response).getOrElse(Json.Null)).isSuccess)
  }

  test("A sendPhoto request can parse the corresponding response") {
    val request = SendPhoto(token, chatId, RemoteMedia("<IMAGE_URL>"), Some("caption"))
    val response = TestingUtils.getJsonString("messages/SendPhotoResponse.json")
    // Test parsing failure
    assert(request.parseSuccessfulResponse(Json.Null).isFailure)
    // Test parsing success
    assert(request.parseSuccessfulResponse(parse(response).getOrElse(Json.Null)).isSuccess)
  }

  test("An answerCallbackQuery request can parse the corresponding response") {
    val request = AnswerCallbackQuery(token, "<CALLBACK_ID>", Some("message"), Some(false), Some("url"), Some(0))
    assert(request.parseSuccessfulResponse(Json.Null) == Success(true))
  }

  test("A getUpdates request can parse the corresponding response") {
    val request = GetUpdates(token, Some(1), Some(2), Some(3))
    val response = TestingUtils.getJsonString("messages/GetUpdatesResponse.json")
    // Test parsing failure
    assert(request.parseSuccessfulResponse(Json.Null) == Success(Nil))
    // Test parsing success
    assert(request.parseSuccessfulResponse(parse(response).getOrElse(Json.Null)).isSuccess)
  }

  test("A deleteMessage request can parse the corresponding response") {
    val request = DeleteMessage(token, chatId, 0)
    assert(request.parseSuccessfulResponse(Json.Null) == Success(true))
  }

  test("A pinMessage request can parse the corresponding response") {
    val request = PinMessage(token, chatId, 0, Some(false))
    assert(request.parseSuccessfulResponse(Json.Null) == Success(true))
  }

  test("A unpinMessage request can parse the corresponding response") {
    val request = UnpinMessage(token, chatId, 0)
    assert(request.parseSuccessfulResponse(Json.Null) == Success(true))
  }

  test("A unpinAllMessages request can parse the corresponding response") {
    val request = UnpinAllMessages(token, chatId)
    assert(request.parseSuccessfulResponse(Json.Null) == Success(true))
  }

  test("A getMe request can parse the corresponding response") {
    val request = GetMe(token)
    val response = TestingUtils.getJsonString("messages/GetMeResponse.json")
    // Test parsing failure
    assert(request.parseSuccessfulResponse(Json.Null).isFailure)
    // Test parsing success
    assert(request.parseSuccessfulResponse(parse(response).getOrElse(Json.Null)).isSuccess)
  }
}
