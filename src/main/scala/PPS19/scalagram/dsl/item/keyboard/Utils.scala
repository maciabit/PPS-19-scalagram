package PPS19.scalagram.dsl.item.keyboard

import PPS19.scalagram.dsl.reactions.action.Action.MessageBuilder
import PPS19.scalagram.models.{InlineKeyboardButton, InlineKeyboardMarkup, ReplyKeyboardButton, ReplyKeyboardMarkup}

object Utils {
  implicit def buttonToEither(button: ReplyKeyboardButton): Either[ReplyKeyboardButton, List[ReplyKeyboardButton]] =
    Left(button)

  implicit def listToEither(list: List[ReplyKeyboardButton]): Either[ReplyKeyboardButton, List[ReplyKeyboardButton]] =
    Right(list)

  implicit def stringToButtonEither(string: String): Either[ReplyKeyboardButton, List[ReplyKeyboardButton]] =
    Left(ReplyKeyboardButton(string))

  implicit def stringListToButtonEither(list: List[String]): Either[ReplyKeyboardButton, List[ReplyKeyboardButton]] =
    Right(list.map(string => ReplyKeyboardButton(string)))

  implicit def buttonToEither(button: InlineKeyboardButton): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Left(button)

  implicit def listToInlineEither(list: List[InlineKeyboardButton]): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Right(list)

  implicit def stringToInlineButtonEither(string: String): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Left(InlineKeyboardButton(string, callbackData = Some(string)))

  implicit def stringListToInlineButtonEither(list: List[String]): Either[InlineKeyboardButton, List[InlineKeyboardButton]] =
    Right(list.map(string => InlineKeyboardButton(string, callbackData = Some(string))))

  implicit class KeyboardButtonList(button: ReplyKeyboardButton) {
    def ::(button2: ReplyKeyboardButton) = List(button2, button)
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
  implicit class KeyboardButtonList(button: ReplyKeyboardButton) {
    def ::(button2: ReplyKeyboardButton) = List(button2, button)
  }
}

object StringUtils {
  implicit class StringList(string: String) {
    def ::(string2: String) = List(string2, string)
  }
}
