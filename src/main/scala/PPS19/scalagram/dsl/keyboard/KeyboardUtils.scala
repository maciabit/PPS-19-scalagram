package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.dsl.reactions.action.Action.MessageContainer
import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

object KeyboardUtils {

  implicit def stringToMessageContainer(string: String): MessageContainer =
    MessageContainer(string, None, None)

  implicit def stringToButtonContainer(
      string: String
  ): KeyboardButtonContainer =
    KeyboardButtonContainer(string, callbackData = Some(string))

  implicit def stringToButtonRow(string: String): KeyboardRow =
    KeyboardRowImpl(
      Seq(KeyboardButtonContainer(string, callbackData = Some(string)))
    )

  implicit def buttonContainerToButtonRow(
      buttonContainer: KeyboardButtonContainer
  ): KeyboardRow =
    KeyboardRowImpl(Seq(buttonContainer))

  def Keyboard(rows: KeyboardRow*): ReplyKeyboardMarkup =
    ReplyKeyboardMarkup(for {
      row <- rows
    } yield row.buttons map { _.toReplyKeyboardButton })

  def InlineKeyboard(rows: KeyboardRow*): InlineKeyboardMarkup =
    InlineKeyboardMarkup(for {
      row <- rows
    } yield row.buttons map { _.toInlineKeyboardButton })

  implicit class ReplyKeyboardMarkupUtils(markup: ReplyKeyboardMarkup) {
    def withResize: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(
        markup.keyboard,
        Some(true),
        markup.oneTimeKeyboard,
        markup.selective
      )

    def withOneTime: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(
        markup.keyboard,
        markup.resizeKeyboard,
        Some(true),
        markup.selective
      )

    def withSelective: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(
        markup.keyboard,
        markup.resizeKeyboard,
        markup.oneTimeKeyboard,
        Some(true)
      )
  }
}
