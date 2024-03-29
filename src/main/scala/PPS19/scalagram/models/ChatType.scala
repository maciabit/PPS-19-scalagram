package PPS19.scalagram.models

import io.circe.Decoder

/** Enumeration representing the type of a chat, can be either "private", "group", "supergroup" or "channel" */
object ChatType extends Enumeration {
  type ChatType = Value
  val Private, Group, Supergroup, Channel = Value

  implicit val chatTypeDecoder: Decoder[ChatType] =
    Decoder[String].map(s => ChatType.withName(s.capitalize))
}
