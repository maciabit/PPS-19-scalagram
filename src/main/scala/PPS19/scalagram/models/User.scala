package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

sealed trait User {
  def id: Int
  def isBot: Boolean
  def firstName: String
  def lastName: Option[String]
  def username: Option[String]
  def languageCode: Option[String]
  def canJoinGroups: Option[Boolean]
  def canReadAllGroupMessages: Option[Boolean]
  def supportsInlineQueries: Option[Boolean]
}

object User {

  def apply(
      id: Int,
      isBot: Boolean = false,
      firstName: String,
      lastName: Option[String] = None,
      username: Option[String] = None,
      languageCode: Option[String] = None,
      canJoinGroups: Option[Boolean] = None,
      canReadAllGroupMessages: Option[Boolean] = None,
      supportsInlineQueries: Option[Boolean] = None
  ): User = {
    if (isBot) {
      BotUser(
        id,
        isBot,
        firstName,
        lastName,
        username,
        languageCode,
        canJoinGroups,
        canReadAllGroupMessages,
        supportsInlineQueries
      )
    } else {
      HumanUser(
        id,
        isBot,
        firstName,
        lastName,
        username,
        languageCode,
        canJoinGroups,
        canReadAllGroupMessages,
        supportsInlineQueries
      )
    }
  }

  implicit val userDecoder: Decoder[User] =
    Decoder
      .instance[User] { cursor =>
        cursor
          .get[Boolean]("isBot")
          .map {
            case true  => deriveDecoder[BotUser]
            case false => deriveDecoder[HumanUser]
          }
          .flatMap(_.tryDecode(cursor))
      }
      .camelCase

  private final case class HumanUser(
      id: Int,
      isBot: Boolean,
      firstName: String,
      lastName: Option[String] = None,
      username: Option[String] = None,
      languageCode: Option[String] = None,
      canJoinGroups: Option[Boolean] = None,
      canReadAllGroupMessages: Option[Boolean] = None,
      supportsInlineQueries: Option[Boolean] = None
  ) extends User

  private final case class BotUser(
      id: Int,
      isBot: Boolean,
      firstName: String,
      lastName: Option[String] = None,
      username: Option[String] = None,
      languageCode: Option[String] = None,
      canJoinGroups: Option[Boolean] = None,
      canReadAllGroupMessages: Option[Boolean] = None,
      supportsInlineQueries: Option[Boolean] = None
  ) extends User
}
