package PPS19.scalagram.models

import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps

trait InputFile

case class ExistingMedia(fileId: String) extends InputFile
case class RemoteMedia( url: String) extends InputFile
