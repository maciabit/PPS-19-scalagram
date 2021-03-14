package PPS19.scalagram

import PPS19.scalagram.dsl.keyboard.{KeyboardButtonContainer, KeyboardRow}
import PPS19.scalagram.dsl.mode.PollingModeContainer
import PPS19.scalagram.dsl.reaction.MessageContainer
import PPS19.scalagram.dsl.scene.PartialStepContainer
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
    context => context.bot.sendMessage(context.chat.chatId, string)

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
      context.bot.sendMessage(
        context.chat.chatId,
        messageBuilder.message,
        messageBuilder.parseMode,
        replyMarkup = messageBuilder.keyboard match {
          case Some(Left(replyKeyboardMarkup)) => Some(replyKeyboardMarkup)
          case Some(Right(inlineKeyboardMarkup)) =>
            Some(inlineKeyboardMarkup)
          case _ => None
        }
      )

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

  /** Creates a new reply keyboard with the given buttons.
    *
    * @param rows Rows of keyboard buttons
    */
  def Keyboard(rows: KeyboardRow*): ReplyKeyboardMarkup =
    ReplyKeyboardMarkup(for {
      row <- rows
    } yield row.buttons map { _.toReplyKeyboardButton })

  /** Creates a new inline keyboard with the given buttons.
    *
    * @param rows Rows of keyboard buttons
    */
  def InlineKeyboard(rows: KeyboardRow*): InlineKeyboardMarkup =
    InlineKeyboardMarkup(for {
      row <- rows
    } yield row.buttons map { _.toInlineKeyboardButton })

  implicit class ReplyKeyboardMarkupUtils(markup: ReplyKeyboardMarkup) {

    /** Creates a new instance of reply keyboard with the given buttons plus withResize field set true. */
    def withResize: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(markup.keyboard, Some(true), markup.oneTimeKeyboard, markup.selective)

    /** Creates a new instance of reply keyboard with the given buttons plus withOneTime field set true. */
    def withOneTime: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(markup.keyboard, markup.resizeKeyboard, Some(true), markup.selective)

    /** Creates a new instance of reply keyboard with the given buttons plus withSelective field set true. */
    def withSelective: ReplyKeyboardMarkup =
      ReplyKeyboardMarkup(markup.keyboard, markup.resizeKeyboard, markup.oneTimeKeyboard, Some(true))
  }

  /** Creates a new [[KeyboardButtonContainer]] with the given text and callbackData.
    *
    * @param tuple The text and the callbackData of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def Callback(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, callbackData = Some(tuple._2))

  /** Creates a new [[KeyboardButtonContainer]] with the given text and url.
    *
    * @param tuple The text and the url of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def Url(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, url = Some(tuple._2))

  /** Creates a new [[KeyboardButtonContainer]] with the given text and switchInlineQuery.
    *
    * @param tuple The text and the inline query of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def InlineQuery(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, switchInlineQuery = Some(tuple._2))

  /** Creates a new [[KeyboardButtonContainer]] with the given text and switchInlineQueryCurrentChat.
    *
    * @param tuple The text and the inline query of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def CurrentChatInlineQuery(tuple: (String, String)): KeyboardButtonContainer =
    KeyboardButtonContainer(tuple._1, switchInlineQueryCurrentChat = Some(tuple._2))

  /** Creates a new [[KeyboardButtonContainer]] with the given text and pay filed set true.
    *
    * @param text The text of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def Payment(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, pay = Some(true))

  /** Creates a new [[KeyboardButtonContainer]] with the given text and requestContact field set true.
    *
    * @param text The text of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def Contact(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, requestContact = Some(true))

  /** Creates a new [[KeyboardButtonContainer]] with the given text and requestLocation field set true.
    *
    * @param text The text of the button.
    * @return The new created [[KeyboardButtonContainer]].
    */
  def Location(text: String): KeyboardButtonContainer =
    KeyboardButtonContainer(text, requestLocation = Some(true))

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

  /** Creates a new [[MessageContainer]] with the given text and HTML formatting enabled.
    *
    * @param message The text of the message.
    * @return The new created [[MessageContainer]].
    */
  def HTML(message: String): MessageContainer =
    MessageContainer(message, Some("HTML"), None)

  /** Creates a new [[MessageContainer]] with the given text and Markdown formatting enabled.
    *
    * @param message The text of the message.
    * @return The new created [[MessageContainer]].
    */
  def Markdown(message: String): MessageContainer =
    MessageContainer(message, Some("Markdown"), None)

  /** Creates a new [[MessageContainer]] with the given text and Markdown formatting enabled.
    *
    * @param message The text of the message.
    * @return The new created [[MessageContainer]].
    */
  def MarkdownV2(message: String): MessageContainer =
    MessageContainer(message, Some("MarkdownV2"), None)

  /** Creates a new [[PollingModeContainer]] with the default parameters, that can be modified with the class methods.
    *
    * @return The new created [[PollingModeContainer]].
    */
  def Polling: PollingModeContainer = PollingModeContainer()
}
