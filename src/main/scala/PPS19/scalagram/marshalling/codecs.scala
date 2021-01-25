package PPS19.scalagram.marshalling
import io.circe._

object codecs {

  private[PPS19] implicit class DecoderOps[A](private val decoder: Decoder[A]) extends AnyVal {
    def camelCase: Decoder[A] =
      decoder.prepare(c => c.focus.map(camelKeys(_).hcursor).getOrElse(c))
  }

  private val camelKeys: Json => Json = transformKeys(_.camelCase)

  private def transformKeys(f: String => String)(json: Json): Json =
    json.arrayOrObject(
      json,
      jArray => Json.fromValues(jArray.map(transformKeys(f))),
      jObject => Json.fromFields(jObject.toList.map { case (k, v) => f(k) -> transformKeys(f)(v) })
    )
}
