package PPS19.scalagram.models

import PPS19.scalagram.models.ChatType.ChatType
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Defines a generic chat.
  *
  * Used by [[PPS19.scalagram.models.PrivateChat]], [[PPS19.scalagram.models.Supergroup]], [[PPS19.scalagram.models.Channel]], [[PPS19.scalagram.models.Group]], [[PPS19.scalagram.models.UnknownChat]]
  */
sealed trait Chat {

  /** Unique identifier of the chat. */
  def id: Long

  /** Unique identifier for the target chat or username of the target channel (in the format @channelusername). */
  def chatId: ChatId = ChatId(id)
}

/** Companion object for Chat. Used as container for implicit methods. */
object Chat {

  implicit val chatDecoder: Decoder[Chat] = Decoder.instance[Chat] { cursor =>
    cursor
      .get[ChatType]("type")
      .map {
        case ChatType.Private    => deriveDecoder[PrivateChat]
        case ChatType.Group      => deriveDecoder[Group]
        case ChatType.Supergroup => deriveDecoder[Supergroup]
        case ChatType.Channel    => deriveDecoder[Channel]
      }
      .flatMap(_.tryDecode(cursor))
  }
}

/** Represents a private chat.
  *
  * @param id        Unique identifier fot this chat.
  * @param username  (Optional) Username of the other party in a private chat if available.
  * @param firstName (Optional) First name of the other party in a private chat.
  * @param lastName  (Optional) Last name of the other party in a private chat.
  */
final case class PrivateChat(
    id: Long,
    username: Option[String] = None,
    firstName: Option[String] = None,
    lastName: Option[String] = None
) extends Chat

/** Represents a group chat.
  *
  * @param id    Unique identifier fot this chat.
  * @param title (Optional) Group title.
  */
final case class Group(id: Long, title: Option[String] = None) extends Chat

/** Represents a supergroup chat.
  *
  * @param id       Unique identifier fot this chat.
  * @param title    (Optional) Supergroup title.
  * @param username (Optional) Username of the supergroup if available.
  */
final case class Supergroup(
    id: Long,
    title: Option[String] = None,
    username: Option[String] = None
) extends Chat

/** Represents a channel chat.
  *
  * @param id       Unique identifier fot this chat.
  * @param title    (Optional) Channel title.
  * @param username (Optional) Username of the group if available.
  */
final case class Channel(
    id: Long,
    title: Option[String] = None,
    username: Option[String] = None
) extends Chat

/** Represents an unknown chat. */
final case class UnknownChat() extends Chat {
  override val id: Long = -1
}
