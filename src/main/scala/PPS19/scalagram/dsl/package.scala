package PPS19.scalagram

import PPS19.scalagram.dsl.keyboard.{KeyboardButtonContainer, KeyboardRow}
import PPS19.scalagram.dsl.mode.PollingModeContainer
import PPS19.scalagram.dsl.reactions.MessageContainer
import PPS19.scalagram.dsl.scenes.steps.PartialStepContainer
import PPS19.scalagram.logic.Context
import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

/** Provides all the required methods, class extensions and implicit conversions needed to build a bot using the Scalagram DSL.
  * It is advised to import all of the contents of this package in the file used to define the bot.
  */
package object dsl {

  /** Trait used to implement the [[*]] wildcard. */
  sealed trait Star {
    def s(): Unit = {}
  }
  private case class StarImpl() extends Star

  /** Value used to indicate that a reaction should match any update of the given type */
  val * : Star = StarImpl()

  // Conversions for the reactions DSL

  /** Implicit conversion from [[String]] to [[Context => Unit]].
    * This conversion enables the use of the following syntax:
    * {{{
    * reactions(
    *   << "Message"
    *   >> "Reply"
    * )}}}
    * as a shortcut, instead of:
    * {{{
    * reactions(
    *   << "Message"
    *   >> { context =>
    *     context.reply("Reply")
    *   }
    * )}}}
    */
  implicit def stringToSendMessage(string: String): Context => Unit =
    context =>
      if (context.chat.nonEmpty) {
        context.bot.sendMessage(context.chat.get.chatId, string)
      }

  /** Implicit conversion from [[MessageContainer]] to [[Context => Unit]].
    * This conversion enables the use of the following syntax:
    * {{{
    * reactions(
    *   << "Message"
    *   >> "Reply" - Keyboard("Button")
    * )}}}
    * as a shortcut, instead of:
    * {{{
    * reactions(
    *   << "Message"
    *   >> { context =>
    *     context.reply("Reply", Some(Keyboard("Button")))
    *   }
    * )}}}
    */
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

  // Conversions for the keyboard DSL

  /** Implicit conversion from [[String]] to [[MessageContainer]], that enables the following syntax:
    * {{{
    *  reactions(
    *    << "Message"
    *    >> "Reply" - Keyboard("Button")
    *  )}}}
    * as a shortcut, instead of:
    * {{{
    *  reactions(
    *    << "Message"
    *    >> MessageContainer("Reply", None, Some(Left(Keyboard("Button"))))
    *  )}}}
    */
  implicit def stringToMessageContainer(string: String): MessageContainer =
    MessageContainer(string, None, None)

  /** Implicit conversion from [[String]] to [[KeyboardButtonContainer]], that enables the use of the syntax
    * {{{Keyboard("Button")}}} as a shortcut, instead of {{{Keyboard(KeyboardButtonContainer("Button"))}}}
    */
  implicit def stringToButtonContainer(string: String): KeyboardButtonContainer =
    Callback(string -> string)

  /** Implicit conversion from [[String]] to [[KeyboardRow]], that enables the use of the syntax
    * {{{Keyboard("Button")}}} as a shortcut, instead of {{{Keyboard(KeyboardRow(KeyboardButtonContainer("Button")))}}}
    */
  implicit def stringToButtonRow(string: String): KeyboardRow =
    KeyboardRow(Seq(Callback(string -> string)))

  /** Implicit conversion from [[String]] to [[KeyboardButtonContainer]], that enables the use of the syntax
    * {{{Keyboard("Button")}}} as a shortcut, instead of {{{Keyboard(KeyboardRow(KeyboardButtonContainer("Button")))}}}
    */
  implicit def buttonContainerToButtonRow(buttonContainer: KeyboardButtonContainer): KeyboardRow =
    KeyboardRow(Seq(buttonContainer))

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

  /** String extension for the Scalagram DSL. */
  implicit class StringExtension(string: String) {

    /** Creates a [[List]] from this string and the one given as a parameter. */
    def |(string2: String) = List(string2, string)

    /** Creates a [[PartialStepContainer]] with this string as name. */
    def <|(stepName: String): PartialStepContainer = PartialStepContainer(string, stepName, Nil)
  }

  /** List extension for the Scalagram DSL. */
  implicit class ListExtension(list: List[String]) {

    /** Alias for [[list.appended]]. */
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
