package PPS19.scalagram

import PPS19.scalagram.dsl.keyboard.{KeyboardButtonContainer, KeyboardRow, KeyboardRowImpl}
import PPS19.scalagram.dsl.mode.PollingModeContainer
import PPS19.scalagram.dsl.reactions.MessageContainer
import PPS19.scalagram.dsl.scenes.steps.PartialStepContainer
import PPS19.scalagram.logic.Context
import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

package object dsl {

  // Value used to indicate that a reaction should match any update of the given type
  sealed trait Star {
    def s(): Unit = {}
  }
  private case class StarImpl() extends Star
  val * : Star = StarImpl()

  // Conversions for Reactions DSL

  implicit def stringToSendMessage(string: String): Context => Unit =
    context =>
      if (context.chat.nonEmpty) {
        context.bot.sendMessage(context.chat.get.chatId, string)
      }

  implicit def messageBuilderToAction(messageBuilder: MessageContainer): Context => Unit =
    context =>
      if (context.chat.nonEmpty) {
        context.bot.sendMessage(
          context.chat.get.chatId,
          messageBuilder.message,
          messageBuilder.parseMode,
          replyMarkup = messageBuilder.keyboard match {
            case Some(Left(replyKeyboardMarkup)) => Some(replyKeyboardMarkup)
            case Some(Right(inlineKeyboardMarkup)) =>
              Some(inlineKeyboardMarkup)
            case _ => None
          }
        )
      }

  // Conversions for keyboard DSL

  implicit def stringToMessageContainer(string: String): MessageContainer =
    MessageContainer(string, None, None)

  implicit def stringToButtonContainer(string: String): KeyboardButtonContainer =
    Callback(string -> string)

  implicit def stringToButtonRow(string: String): KeyboardRow =
    KeyboardRowImpl(Seq(Callback(string -> string)))

  implicit def buttonContainerToButtonRow(buttonContainer: KeyboardButtonContainer): KeyboardRow =
    KeyboardRowImpl(Seq(buttonContainer))

  // Keyboard DSL methods

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
      ReplyKeyboardMarkup(markup.keyboard, Some(true), markup.oneTimeKeyboard, markup.selective)

    def withOneTime: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(markup.keyboard, markup.resizeKeyboard, Some(true), markup.selective)

    def withSelective: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(markup.keyboard, markup.resizeKeyboard, markup.oneTimeKeyboard, Some(true))
  }

  // Keyboard buttons DSL methods

  def Callback(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, callbackData = Some(tuple._2))

  def Url(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, url = Some(tuple._2))

  def InlineQuery(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, switchInlineQuery = Some(tuple._2))

  def CurrentChatInlineQuery(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, switchInlineQueryCurrentChat = Some(tuple._2))

  def Payment(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, pay = Some(true))

  def Contact(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, requestContact = Some(true))

  def Location(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, requestLocation = Some(true))

  // Extension classes

  implicit class StringExtension(string: String) {

    def |(string2: String) = List(string2, string)

    def <|(stepName: String): PartialStepContainer = PartialStepContainer(string, stepName, Nil)
  }

  implicit class ListExtension(list: List[String]) {
    def |(string: String): List[String] = string +: list
  }

  // Reactions DSL methods

  def HTML(message: String): MessageContainer =
    MessageContainer(message, Some("HTML"), None)

  def Markdown(message: String): MessageContainer =
    MessageContainer(message, Some("Markdown"), None)

  def MarkdownV2(message: String): MessageContainer =
    MessageContainer(message, Some("MarkdownV2"), None)

  // Mode DSL methods

  def Polling: PollingModeContainer = PollingModeContainer()
}
