package PPS19.scalagram.dsl.item.keyboard

import PPS19.scalagram.dsl.reactions.action.Action.MessageBuilder
import PPS19.scalagram.models.{InlineKeyboardButton, InlineKeyboardMarkup, KeyboardButton, ReplyKeyboardMarkup}

object Utils {
  implicit def buttonToEither(button: KeyboardButton): Either[KeyboardButton, List[KeyboardButton]] =
    Left(button)

  implicit def listToEither(list: List[KeyboardButton]): Either[KeyboardButton, List[KeyboardButton]] =
    Right(list)

  implicit def stringToButtonEither(string: String): Either[KeyboardButton, List[KeyboardButton]] =
    Left(KeyboardButton(string))

  implicit def stringListToButtonEither(list: List[String]): Either[KeyboardButton, List[KeyboardButton]] =
    Right(list.map(string => KeyboardButton(string)))

  implicit def buttonToEither(button: InlineKeyboardButton): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Left(button)

  implicit def listToInlineEither(list: List[InlineKeyboardButton]): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Right(list)

  implicit def stringToInlineButtonEither(string: String): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Left(InlineKeyboardButton(string, callbackData = Some(string)))

  implicit def stringListToInlineButtonEither(list: List[String]): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Right(list.map(string => InlineKeyboardButton(string, callbackData = Some(string))))

  implicit class KeyboardButtonList(button: KeyboardButton) {
    def ::(button2: KeyboardButton) = List(button2, button)
  }

  implicit class InlineKeyboardButtonList(button: InlineKeyboardButton) {
    def ::(button2: InlineKeyboardButton) = List(button2, button)
  }

  implicit class StringExtension(string: String) {

    def ::(string2: String) = List(string2, string)

    def ::(button2: InlineKeyboardButton) = List(button2, InlineKeyboardButton(string, callbackData = Some(string)))

    def -(keyboard: ReplyKeyboardMarkup): MessageBuilder = MessageBuilder(string, None, Some(Left(keyboard)))

    def -(keyboard: InlineKeyboardMarkup): MessageBuilder = MessageBuilder(string, None, Some(Right(keyboard)))

    def callback(callbackData: String): InlineKeyboardButton = InlineKeyboardButton(string, callbackData = Some(callbackData))

    def link(url: String): InlineKeyboardButton = InlineKeyboardButton(string, url = Some(url))

  }
}

object InlineKeyboardButtonUtils {
  implicit class InlineKeyboardButtonList(button: InlineKeyboardButton) {

    def ::(button2: InlineKeyboardButton) = List(button2, button)

    def ::(button2: String) = List(InlineKeyboardButton(button2, callbackData = Some(button2)), button)
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
