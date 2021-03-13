package PPS19.scalagram.models

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Defines a generic message entity, a piece of text with peculiar charateristics.
  *
  * Used by [[PPS19.scalagram.models.MessageEntity.Mention]], [[PPS19.scalagram.models.MessageEntity.Hashtag]], [[PPS19.scalagram.models.MessageEntity.Cashtag]],
  * [[PPS19.scalagram.models.MessageEntity.Url]], [[PPS19.scalagram.models.MessageEntity.Email]], [[PPS19.scalagram.models.MessageEntity.PhoneNumber]],
  * [[PPS19.scalagram.models.MessageEntity.Bold]], [[PPS19.scalagram.models.MessageEntity.Italic]], [[PPS19.scalagram.models.MessageEntity.Code]],
  * [[PPS19.scalagram.models.MessageEntity.Pre]], [[PPS19.scalagram.models.MessageEntity.TextLink]], [[PPS19.scalagram.models.MessageEntity.TextMention]],
  * [[PPS19.scalagram.models.MessageEntity.Underline]], [[PPS19.scalagram.models.MessageEntity.Strikethrough]] and [[PPS19.scalagram.models.MessageEntity.Unknown]].
  */
sealed trait MessageEntity {

  /** Offset in UTF-16 code units to the start of the entity. */
  def offset: Int

  /** Length of the entity in UTF-16 code units.*/
  def length: Int
}

/** Companion object for MessageEntity. Used as container for implicit methods. */
object MessageEntity {

  implicit val chatDecoder: Decoder[MessageEntity] =
    Decoder.instance[MessageEntity] { cursor =>
      cursor
        .get[String]("type")
        .map {
          case "mention"       => deriveDecoder[Mention]
          case "hashtag"       => deriveDecoder[Hashtag]
          case "cashtag"       => deriveDecoder[Cashtag]
          case "url"           => deriveDecoder[Url]
          case "email"         => deriveDecoder[Email]
          case "phone_number"  => deriveDecoder[PhoneNumber]
          case "bold"          => deriveDecoder[Bold]
          case "italic"        => deriveDecoder[Italic]
          case "code"          => deriveDecoder[Code]
          case "pre"           => deriveDecoder[Pre]
          case "text_link"     => deriveDecoder[TextLink]
          case "text_mention"  => deriveDecoder[TextMention]
          case "underline"     => deriveDecoder[Underline]
          case "strikethrough" => deriveDecoder[Strikethrough]
          case _               => deriveDecoder[Unknown]
        }
        .flatMap(_.tryDecode(cursor))
    }

  /** Represents a user mention (@username) inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Mention(offset: Int, length: Int) extends MessageEntity

  /** Represents an hashtag inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Hashtag(offset: Int, length: Int) extends MessageEntity

  /** Represents a cash tag inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Cashtag(offset: Int, length: Int) extends MessageEntity

  /** Represents a command directed to a bot inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class BotCommand(offset: Int, length: Int) extends MessageEntity

  /** Represents an URL inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Url(offset: Int, length: Int) extends MessageEntity

  /** Represents an email inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Email(offset: Int, length: Int) extends MessageEntity

  /** Represents a phone number inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class PhoneNumber(offset: Int, length: Int) extends MessageEntity

  /** Represents a bold text inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Bold(offset: Int, length: Int) extends MessageEntity

  /** Represents an italic text inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Italic(offset: Int, length: Int) extends MessageEntity

  /** Represents a monodwidth string inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Code(offset: Int, length: Int) extends MessageEntity

  /** Represents a monowidth block inside a text message.
    *
    * @param offset   Offset in UTF-16 code units to the start of the entity.
    * @param length   Length of the entity in UTF-16 code units.
    * @param language (Optional) The programming language of the enity text.
    *
    *                 Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Pre(offset: Int, length: Int, language: Option[String]) extends MessageEntity

  /** Represents a clickable URL inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity.
    * @param length Length of the entity in UTF-16 code units.
    * @param url    Url that will be opened after tapping on the text.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class TextLink(offset: Int, length: Int, url: String) extends MessageEntity

  /** Represents a user mention for user without username inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity
    * @param length Length of the entity in UTF-16 code units.
    * @param user   The mentioned user.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class TextMention(offset: Int, length: Int, user: User) extends MessageEntity

  /** Represents an underlined text inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Underline(offset: Int, length: Int) extends MessageEntity

  /** Represents a strikethrough text inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Strikethrough(offset: Int, length: Int) extends MessageEntity

  /** Represents an unknown message entity inside a text message.
    *
    * @param offset Offset in UTF-16 code units to the start of the entity
    * @param length Length of the entity in UTF-16 code units.
    *
    *               Extends[[PPS19.scalagram.models.MessageEntity]]
    */
  case class Unknown(offset: Int, length: Int) extends MessageEntity
}
