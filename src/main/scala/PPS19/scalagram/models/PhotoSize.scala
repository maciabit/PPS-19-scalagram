package PPS19.scalagram.models

import cats.syntax.functor._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

/** Represents a the size of a photo or a file / sticker thumbnail.
  *
  * Used by [[PPS19.scalagram.models.Size]]
  */
sealed trait PhotoSize {
  def fileId: String
  def fileUniqueId: String
  def width: Int
  def height: Int
  def fileSize: Option[Int]
}

/** Companion object for PhotoSize. Used as container for implicit methods. */
object PhotoSize {

  implicit val photoSizeDecoder: Decoder[PhotoSize] = List[Decoder[PhotoSize]](
    deriveDecoder[Size].widen
  ).head
}

/** Represents the size of a photo.
  *
  * @param fileId       Identifier of the file. Can be used to dowlonad or reuse it.
  * @param fileUniqueId Unique identifier of the file, which is the same over time and in different bots.
  * @param width        The photo width.
  * @param height       The photo height.
  * @param fileSize     (Optional) The total photo size.
  *                     Extends [[PPS19.scalagram.models.PhotoSize]].
  */
final case class Size(
    fileId: String,
    fileUniqueId: String,
    width: Int,
    height: Int,
    fileSize: Option[Int] = None
) extends PhotoSize
