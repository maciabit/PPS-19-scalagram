package PPS19.scalagram.models

trait InputFile

case class ExistingMedia(fileId: String) extends InputFile
case class RemoteMedia(url: String) extends InputFile
case class UploadMedia(path: String) extends InputFile
