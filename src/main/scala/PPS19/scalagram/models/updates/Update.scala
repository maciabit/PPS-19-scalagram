package PPS19.scalagram.models.updates

import PPS19.scalagram.marshalling.codecs.DecoderOps
import PPS19.scalagram.models.updates.UpdateType.UpdateType
import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Represents an incoming update for a bot.
  * Used by [[ChatUpdate]], [[UnknownUpdate]],
  */
trait Update {

  /** The update's unique identifier. Update identifiers starts from a certain positive number and increase sequentially. */
  val updateId: Long

  /** Type of the update */
  //noinspection MutatorLikeMethodIsParameterless
  def updateType: UpdateType
}

/** Companion object for Update. Used as container for implicit methods. */
object Update {
  implicit val updateDecoder: Decoder[Update] = List[Decoder[Update]](
    deriveDecoder[MessageReceived].widen,
    deriveDecoder[MessageEdited].widen,
    deriveDecoder[ChannelPostReceived].widen,
    deriveDecoder[ChannelPostEdited].widen,
    deriveDecoder[CallbackButtonSelected].widen,
    deriveDecoder[UnknownUpdate].widen
  ).reduceLeft(_.or(_)).camelCase
}
