package PPS19.scalagram.dsl

import PPS19.scalagram.models.KeyboardButton

object Conversions {

  implicit def buttonToEither(
      button: KeyboardButton
  ): Either[KeyboardButton, List[KeyboardButton]] = Left(button)

  implicit def listToEither(
      list: List[KeyboardButton]
  ): Either[KeyboardButton, List[KeyboardButton]] = Right(list)

  implicit def stringToButtonEither(
      string: String
  ): Either[KeyboardButton, List[KeyboardButton]] = Left(KeyboardButton(string))

  implicit def stringListToButtonEither(
    list: List[String]
  ): Either[KeyboardButton, List[KeyboardButton]] = Right(list.map(string => KeyboardButton(string)))
}
