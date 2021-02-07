package PPS19.scalagram.models

import cats.syntax.functor._
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

  /**
    * Decodes chat based on the `type` value of the input Json
    */
  implicit val userDecoder: Decoder[User] = List[Decoder[User]](
    deriveDecoder[HumanUser].widen,
    deriveDecoder[BotUser].widen
  ).reduceLeft(_.or(_))
}

final case class HumanUser(
    id: Int,
    isBot: Boolean = false,
    firstName: String,
    lastName: Option[String],
    username: Option[String],
    languageCode: Option[String],
    canJoinGroups: Option[Boolean],
    canReadAllGroupMessages: Option[Boolean],
    supportsInlineQueries: Option[Boolean]
) extends User

final case class BotUser(
    id: Int,
    isBot: Boolean = true,
    firstName: String,
    lastName: Option[String],
    username: Option[String],
    languageCode: Option[String],
    canJoinGroups: Option[Boolean],
    canReadAllGroupMessages: Option[Boolean],
    supportsInlineQueries: Option[Boolean]
) extends User
