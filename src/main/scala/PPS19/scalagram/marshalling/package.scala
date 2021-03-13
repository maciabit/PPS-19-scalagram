package PPS19.scalagram

/** Object used as container of implicits for Strings.
  * Used to transform a string writing style.
  */
package object marshalling {
  implicit class CaseString(private val word: String) extends AnyVal {

    /** Transform a string in camelCase */
    def camelCase: String =
      if (word.isEmpty) word
      else word.substring(0, 1).toLowerCase + word.pascalCase.substring(1)

    /** Transform a string in PascalCase */
    def pascalCase: String = word.split('_').map(_.capitalize).mkString("")

    /** Transform a string in snake_case */
    def snakeCase: String = {
      val spacesPattern = "[-\\s]".r
      val firstPattern = "([A-Z]+)([A-Z][a-z])".r
      val secondPattern = "([a-z\\d])([A-Z])".r
      val replacementPattern = "$1_$2"
      spacesPattern
        .replaceAllIn(
          secondPattern.replaceAllIn(
            firstPattern.replaceAllIn(word, replacementPattern),
            replacementPattern
          ),
          "_"
        )
        .toLowerCase
    }
  }
}
