package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps

trait InputFile {
  def fileId: Option[String]
  def url: Option[String]
}

object InputFile {
  implicit val encodeInputFile : Encoder[InputFile] = Encoder.instance {
    case existingMedia: ExistingMedia => existingMedia.asJson.deepDropNullValues
    case remoteMedia: RemoteMedia => remoteMedia.asJson.deepDropNullValues
  }
}

case class ExistingMedia(fileId: Option[String], url: Option[String] = None) extends InputFile
case class RemoteMedia(fileId: Option[String] = None, url: Option[String]) extends InputFile
