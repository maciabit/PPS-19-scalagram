package PPS19.scalagram.models

import PPS19.scalagram.marshalling.codecs.DecoderOps
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Defines a generic Telegram user, human or bot. */
sealed trait User {

  /** Unique identifier for this user. */
  def id: Int

  /** True if the user is a bot, false otherwise. */
  def isBot: Boolean

  /** User's first name. */
  def firstName: String

  /** (Optional) User's last name. */
  def lastName: Option[String]

  /** (Optional) User's username. */
  def username: Option[String]
}

/** Companion object for User. Used as container for implicit methods and as a Factory. */
object User {

  /** Creates a HumanUser or a BotUser, depending on the passed value, wth the given parameters.
    *
    * @param id                      Unique identifier for this user
    * @param isBot                   True if the user is a bot, false otherwise.
    * @param firstName               User's first name.
    * @param lastName                (Optional) User's last name.
    * @param username                (Optional) User's username.
    * @param languageCode            (Optional)  IETF language tag of the user's language.
    * @param canJoinGroups           (Optional) If true, the bot che ne invited to groups.
    * @param canReadAllGroupMessages (Optional) If true, the privacy mode is disabled for the bot.
    * @param supportsInlineQueries   (Optional)  If true, the bot supports inline queries.
    * @return The created HumanUser or BotUser
    */
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
        languageCode
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
      languageCode: Option[String] = None
  ) extends User

  private final case class BotUser(
      id: Int,
      isBot: Boolean,
      firstName: String,
      lastName: Option[String] = None,
      username: Option[String] = None,
      canJoinGroups: Option[Boolean] = None,
      canReadAllGroupMessages: Option[Boolean] = None,
      supportsInlineQueries: Option[Boolean] = None
  ) extends User
}
