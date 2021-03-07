package PPS19.scalagram.methods

import PPS19.scalagram.logic.BotToken
import PPS19.scalagram.models.User
import io.circe.Json
import io.circe.parser.decode
import requests.Requester

import scala.util.{Failure, Success, Try}

case class GetMe(token: BotToken) extends TelegramRequest[User] {
  override val request: Requester = requests.get
  override val endpoint: String = "getMe"
  override val urlParams: Map[String, Any] = Map.empty

  override def parseSuccessfulResponse(json: Json): Try[User] = {
    println(json.toString())
    decode[User](json.toString()) match {
      case Right(message) => Success(message)
      case Left(error)    => Failure(error)
    }
  }
}
