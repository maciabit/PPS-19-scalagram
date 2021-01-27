package PPS19.scalagram.models

import PPS19.scalagram.models.ChatType.ChatType
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

sealed trait Chat {
  def id:Long
}

object Chat {

  /**
   * Decodes chat based on the `type` value of the input Json
   */
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

final case class PrivateChat(id: Long, username: Option[String], firstName: Option[String], lastName: Option[String])
  extends Chat

final case class Group(id: Long, title: Option[String]) extends Chat

final case class Supergroup(id: Long, title: Option[String], username: Option[String]) extends Chat

final case class Channel(id: Long, title: Option[String], username: Option[String]) extends Chat