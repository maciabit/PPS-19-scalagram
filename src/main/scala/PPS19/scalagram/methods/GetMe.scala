package PPS19.scalagram.methods

import PPS19.scalagram.models.{BotToken, User}
import io.circe.Json
import io.circe.parser.decode
import requests.Requester

import scala.util.{Failure, Success, Try}

/** A simple method for testing your bot's auth token. Requires no parameters. Returns basic information about the bot in form of a User object.
  *
  * @param token Token of the bot that will perform the requets.
  */
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
