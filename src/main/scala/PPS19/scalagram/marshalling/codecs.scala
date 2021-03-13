package PPS19.scalagram.marshalling
import io.circe._

/** Object used as container of implicits for Circe Decoder and Encoder.
  * Used to transform object or json fields writing style.
  */
object codecs {

  private[PPS19] implicit class DecoderOps[A](private val decoder: Decoder[A]) extends AnyVal {
    def camelCase: Decoder[A] =
      decoder.prepare(c => c.focus.map(camelKeys(_).hcursor).getOrElse(c))
  }

  private[PPS19] implicit class EncoderOps[A](private val encoder: Encoder[A]) extends AnyVal {
    def snakeCase: Encoder[A] =
      encoder.mapJson(j =>
        parser
          .parse(printer.print(snakeKeys(j)))
          .getOrElse(
            throw new RuntimeException(
              "Exception during encoding with snake_case"
            )
          )
      )
  }

  private val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  private val camelKeys: Json => Json = transformKeys(_.camelCase)
  private val snakeKeys: Json => Json = transformKeys(_.snakeCase)

  private def transformKeys(f: String => String)(json: Json): Json =
    json.arrayOrObject(
      json,
      jArray => Json.fromValues(jArray.map(transformKeys(f))),
      jObject =>
        Json.fromFields(jObject.toList.map {
          case (k, v) => f(k) -> transformKeys(f)(v)
        })
    )
}
