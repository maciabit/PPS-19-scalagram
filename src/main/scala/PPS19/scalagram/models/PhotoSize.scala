package PPS19.scalagram.models

import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

sealed trait PhotoSize {
  def fileId: String
  def fileUniqueId: String
  def width: Int
  def height: Int
  def fileSize: Option[Int]
}

object PhotoSize {

  /**
    * Decodes chat based on the `type` value of the input Json
    */
  implicit val photoSizeDecoder: Decoder[PhotoSize] = List[Decoder[PhotoSize]](
    deriveDecoder[Size].widen
  ).head
}
final case class Size(
    fileId: String,
    fileUniqueId: String,
    width: Int,
    height: Int,
    fileSize: Option[Int] = None
) extends PhotoSize
