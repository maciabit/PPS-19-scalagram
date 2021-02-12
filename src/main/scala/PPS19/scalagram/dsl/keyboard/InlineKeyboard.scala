package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.models.{InlineKeyboardButton, InlineKeyboardMarkup, KeyboardButton}

object InlineKeyboard {

  def apply(rows: Either[KeyboardButton, List[KeyboardButton]]*): InlineKeyboardMarkup = {
    InlineKeyboardMarkup(keyboardButtonToInline(rows)(_ => InlineKeyboardButton("Pisto")))
  }

  private def keyboardButtonToInline(buttons: Seq[Either[KeyboardButton, List[KeyboardButton]]])(conversion: KeyboardButton => InlineKeyboardButton):
  Seq[Seq[InlineKeyboardButton]] =
    buttons.map {
      case Left(button) => List(conversion(button))
      case Right(tuple) => tuple.map(conversion)
    }
}
