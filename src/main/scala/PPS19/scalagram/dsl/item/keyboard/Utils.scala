package PPS19.scalagram.dsl.item.keyboard

import PPS19.scalagram.models.KeyboardButton

object Utils {
  implicit def buttonToEither(button: KeyboardButton): Either[KeyboardButton, List[KeyboardButton]] =
    Left(button)

  implicit def listToEither(list: List[KeyboardButton]): Either[KeyboardButton, List[KeyboardButton]] =
    Right(list)

  implicit def stringToButtonEither(string: String): Either[KeyboardButton, List[KeyboardButton]] =
    Left(KeyboardButton(string))

  implicit def stringListToButtonEither(list: List[String]): Either[KeyboardButton, List[KeyboardButton]] =
    Right(list.map(string => KeyboardButton(string)))

  implicit class KeyboardButtonList(button: KeyboardButton) {
    def ::(button2: KeyboardButton) = List(button2, button)
  }

  implicit class StringList(string: String) {
    def ::(string2: String) = List(string2, string)
  }
}

object KeyboardButtonUtils {
  implicit class KeyboardButtonList(button: KeyboardButton) {
    def ::(button2: KeyboardButton) = List(button2, button)
  }
}

object StringUtils {
  implicit class StringList(string: String) {
    def ::(string2: String) = List(string2, string)
  }
}
