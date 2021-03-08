package PPS19.scalagram.models

/** Represents the content of a file to be uploaded.
  *
  * Used by [[PPS19.scalagram.models.ExistingMedia]], [[PPS19.scalagram.models.RemoteMedia]], [[PPS19.scalagram.models.UploadMedia]]
  */
trait InputFile

/** Represent a file to be uploaded already stored on Telegram servers, so it is not necessary to reupload it.
  *
  * @param fileId Unique identifier of the file.
  */
case class ExistingMedia(fileId: String) extends InputFile

/** Represent a file to be uploaded accessible via its URL. Telegram will download the file and store on its servers.
  *
  * @param url The file URL.
  */
case class RemoteMedia(url: String) extends InputFile

/** Represent a file to be uploaded posted using a multiplart/form-data in the usual way that files are uploaded via the browser0. Telegram will download the file and store on its servers.
  *
  * @param path The path of the file that will be uploaded.
  */
case class UploadMedia(path: String) extends InputFile
