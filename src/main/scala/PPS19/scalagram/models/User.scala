package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
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

  def apply(
      id: Int,
      isBot: Boolean,
      firstName: String,
      lastName: Option[String] = None,
      username: Option[String] = None,
      languageCode: Option[String] = None,
      canJoinGroups: Option[Boolean] = None,
      canReadAllGroupMessages: Option[Boolean] = None,
      supportsInlineQueries: Option[Boolean] = None
  ): User =
    UserImpl(
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

  /**
    * Decodes chat based on the `type` value of the input Json
    */
  implicit val userDecoder: Decoder[User] = List[Decoder[User]](
    deriveDecoder[UserImpl].widen
  ).reduceLeft(_.or(_)).camelCase

  private final case class UserImpl(
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
