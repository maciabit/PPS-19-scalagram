package PPS19.scalagram.logic

import PPS19.scalagram.logic.reactions._
import PPS19.scalagram.logic.scenes.Scene
import PPS19.scalagram.methods._
import PPS19.scalagram.models._
import PPS19.scalagram.models.payloads.TelegramMessage
import PPS19.scalagram.models.updates.Update
import PPS19.scalagram.modes.polling.Mode

import scala.util.{Success, Try}

/** A bot created with the Scalagram library */
trait Bot {

  /** The bot's token */
  val token: BotToken

  /** The bot's middlewares, in order of execution */
  val middlewares: List[Middleware]

  /** The bot's reactions, in order of priority (descending) */
  val reactions: List[Reaction]

  /** The bot's scenes */
  val scenes: List[Scene]

  /** The bot's Telegram User */
  val user: Option[User] = getMe match {
    case Success(user) => Some(user)
    case _             => None
  }

  /** Launches the bot.
    *
    * @param mode Mode that the bot uses to fetch updates.
    *             Right now, only [[PPS19.scalagram.modes.polling.Polling]] is supported.
    */
  def launch(mode: Mode): Unit = mode.start(this)

  /** Use this method to receive incoming updates using long polling (wiki). A List of Update objects is returned.
    *
    * @param offset         Identifier of the first update to be returned.
    *                       Must be greater by one than the highest among the identifiers of previously received updates.
    *                       By default, updates starting with the earliest unconfirmed update are returned.
    *                       An update is considered confirmed as soon as getUpdates is called with an offset higher than its updateId.
    *                       The negative offset can be specified to retrieve updates starting from -offset update from the end of the updates queue.
    *                       All previous updates will forgotten.
    * @param limit          Limits the number of updates to be retrieved. Values between 1-100 are accepted.
    *                       Defaults to 100.
    * @param timeout        Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling.
    * @param allowedUpdates List the types of updates you want your bot to receive.
    *                       Specify an empty list to receive all updates regardless of type (default).
    *                       If not specified, the previous setting will be used.
    *                       Please note that this parameter doesn't affect updates created before the call to the getUpdates,
    *                       so unwanted updates may be received for a short period of time.
    */
  def getUpdates(
      offset: Option[Long] = None,
      limit: Option[Int] = None,
      timeout: Option[Int] = None,
      allowedUpdates: Option[Array[String]] = None
  ): Try[List[Update]] = GetUpdates(token, offset, limit, timeout, allowedUpdates).call()

  /** Use this method to send text messages. On success, the sent [[TelegramMessage]] is returned.
    *
    * @param chatId                   Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    * @param text                     Text of the message to be sent, 1-4096 characters after entities parsing.
    * @param parseMode                Mode for parsing entities in the message text.
    *                                 See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
    * @param entities                 List of special entities that appear in message text, which can be specified instead of parse_mode.
    * @param disablePreview           Disables link previews for links in this message.
    * @param disableNotification      Sends the message silently. Users will receive a notification with no sound.
    * @param replyToMessageId         If the message is a reply, ID of the original message.
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found.
    * @param replyMarkup              Additional interface options.
    *                                 Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
    *                                 [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
    */
  def sendMessage(
      chatId: ChatId,
      text: String,
      parseMode: Option[String] = None,
      entities: Option[Vector[Any]] = None,
      disablePreview: Option[Boolean] = None,
      disableNotification: Option[Boolean] = None,
      replyToMessageId: Option[Int] = None,
      allowSendingWithoutReply: Option[Boolean] = None,
      replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage] =
    SendMessage(
      token,
      chatId,
      text,
      parseMode,
      entities,
      disablePreview,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    ).call()

  /** Use this method to edit text messages.
    * On success, the edited [[TelegramMessage]] is returned.
    *
    * @param chatId          Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    * @param text            Text of the message to be sent, 1-4096 characters after entities parsing.
    * @param messageId       Required if inline_message_id is not specified. Identifier of the message to edit.
    * @param inlineMessageId Required if chat_id and message_id are not specified. Identifier of the inline message.
    * @param parseMode       Mode for parsing entities in the message text.
    *                        See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
    * @param entities        List of special entities that appear in message text, which can be specified instead of parse_mode.
    * @param disablePreview  Disables link previews for links in this message.
    * @param replyMarkup     Additional interface options.
    *                        Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
    *                        [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
    */
  def editMessage(
      chatId: ChatId,
      text: String,
      messageId: Option[Integer] = None,
      inlineMessageId: Option[String] = None,
      parseMode: Option[String] = None,
      entities: Option[Vector[Any]] = None,
      disablePreview: Option[Boolean] = None,
      replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage] =
    EditMessage(
      token,
      chatId,
      text,
      messageId,
      inlineMessageId,
      parseMode,
      entities,
      disablePreview,
      replyMarkup
    ).call()

  /** Use this method to send photos. On success, the sent [[TelegramMessage]] is returned.
    *
    * @param chatId                   Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    * @param photo                    Photo to send.
    *                                 Pass a file_id as String to send a photo that exists on the Telegram servers (recommended),
    *                                 pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using multipart/form-data.
    * @param caption                  Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing.
    * @param parseMode                Mode for parsing entities in the message text.
    *                                 See [[https://core.telegram.org/bots/api#formatting-options formatting options]] for more details.
    * @param entities                 List of special entities that appear in message text, which can be specified instead of parse_mode.
    * @param disableNotification      Sends the message silently. Users will receive a notification with no sound.
    * @param replyToMessageId         If the message is a reply, ID of the original message.
    * @param allowSendingWithoutReply Pass True, if the message should be sent even if the specified replied-to message is not found.
    * @param replyMarkup              Additional interface options.
    *                                 Can receive an instance of [[PPS19.scalagram.models.ReplyKeyboardMarkup]], [[PPS19.scalagram.models.InlineKeyboardMarkup]],
    *                                 [[PPS19.scalagram.models.ReplyKeyboardRemove]] or [[PPS19.scalagram.models.ForceReply]].
    */
  def sendPhoto(
      chatId: ChatId,
      photo: InputFile,
      caption: Option[String] = None,
      parseMode: Option[String] = None,
      entities: Option[Vector[Any]] = None,
      disableNotification: Option[Boolean] = None,
      replyToMessageId: Option[Int] = None,
      allowSendingWithoutReply: Option[Boolean] = None,
      replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage] =
    SendPhoto(
      token,
      chatId,
      photo,
      caption,
      parseMode,
      entities,
      disableNotification,
      replyToMessageId,
      allowSendingWithoutReply,
      replyMarkup
    ).call()

  /** Use this method to delete a message, including service messages.
    *
    * @param chatId    Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    * @param messageId Identifier of the message to delete
    */
  def deleteMessage(chatId: ChatId, messageId: Int): Try[Boolean] = DeleteMessage(token, chatId, messageId).call()

  /** Use this method to add a message to the list of pinned messages in a chat.
    * If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have
    * the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
    * Returns true on success, false otherwise.
    *
    * @param chatId              Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    * @param messageId           Identifier of a message to pin.
    * @param disableNotification Pass true, if it is not necessary to send a notification to all chat members about the new pinned message.
    *                            Notifications are always disabled in channels and private chats.
    */
  def pinMessage(
      chatId: ChatId,
      messageId: Int,
      disableNotification: Option[Boolean]
  ): Try[Boolean] =
    PinMessage(token, chatId, messageId, disableNotification).call()

  /** Use this method to remove a message from the list of pinned messages in a chat.
    * If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have
    * the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
    * Returns true on success, false otherwise.
    *
    * @param chatId    Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    * @param messageId Identifier of a message to unpin.
    */
  def unpinMessage(chatId: ChatId, messageId: Int): Try[Boolean] = UnpinMessage(token, chatId, messageId).call()

  /** Use this method to clear the list of pinned messages in a chat.
    * If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have
    * the 'can_pin_messages' admin right in a supergroup or 'can_edit_messages' admin right in a channel.
    * Returns true on success, false otherwise.
    *
    * @param chatId Unique identifier for the target chat or username of the target channel (in the format @channelusername).
    */
  def unpinAllMessages(chatId: ChatId): Try[Boolean] = UnpinAllMessages(token, chatId).call()

  /** Use this method to send answers to callback queries sent from inline keyboards.
    * The answer will be displayed to the user as a notification at the top of the chat screen or as an alert.
    *
    * @param callbackQueryId Unique identifier for the query to be answered.
    * @param text            Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters.
    * @param showAlert       If true, an alert will be shown by the client instead of a notification at the top of the chat screen.
    *                        Defaults to false.
    * @param url             URL that will be opened by the user's client.
    * @param cacheTime       The maximum amount of time in seconds that the result of the callback query may be cached client-side.
    *                        Defaults to 0.
    */
  def answerCallbackQuery(
      callbackQueryId: String,
      text: Option[String],
      showAlert: Option[Boolean],
      url: Option[String],
      cacheTime: Option[Int]
  ): Try[Boolean] = AnswerCallbackQuery(token, callbackQueryId, text, showAlert, url, cacheTime).call()

  /** A simple method for testing your bot's auth token. Requires no parameters. Returns basic information about the bot in form of a User object. */
  def getMe: Try[User] = GetMe(token).call()
}

/** Companion object for Bot. */
object Bot {

  /** Creates a bot with the given parameters.
    *
    * @param token       The bot's token
    * @param middlewares The bot's middlewares, in order of execution
    * @param reactions   The bot's reactions, in order of priority (descending)
    * @param scenes      The bot's scenes
    */
  def apply(
      token: BotToken,
      middlewares: List[Middleware] = List.empty,
      reactions: List[Reaction] = List.empty,
      scenes: List[Scene] = List.empty
  ): Bot = BotImpl(token, middlewares, scenes, reactions)

  /** Extractor function for the Bot trait. */
  def unapply(bot: Bot): Option[(BotToken, List[Middleware], List[Reaction], List[Scene])] =
    Some(bot.token, bot.middlewares, bot.reactions, bot.scenes)

  private case class BotImpl(
      token: BotToken,
      middlewares: List[Middleware],
      scenes: List[Scene],
      reactions: List[Reaction]
  ) extends Bot

  /** A reaction that only gets executed if the update is a message equal to one of the given strings.
    *
    * @param strings Messages that allow the action to be triggered.
    *                If no messages are passed, the action will be triggered by any text message.
    * @param action The function to be executed
    */
  def onMessage(strings: String*)(action: Context => Unit): Reaction = OnMessage(strings: _*).build(action)

  /** A reaction that only gets executed if the update is a "/start" message.
    *
    * @param action The function to be executed
    */
  def onStart(action: Context => Unit): Reaction = OnStart().build(action)

  /**
    * A reaction that only gets executed if the update is a "/help" message.
    *
    * @param action The function to be executed
    */
  def onHelp(action: Context => Unit): Reaction = OnHelp().build(action)

  /** A reaction that only gets executed if the update is an edited message equal to one of the given strings.
    *
    * @param strings Messages that allow the action to be triggered.
    *                If no messages are passed, the action will be triggered by any edited text message.
    * @param action The function to be executed
    */
  def onMessageEdited(strings: String*)(action: Context => Unit): Reaction = OnMessageEdited(strings: _*).build(action)

  /** A reaction that only gets executed if the update is a callback query with the data property equal to the given string.
    *
    * @param callbackData Callback data that allows the action to be triggered.
    * @param action       The function to be executed
    */
  def onCallbackQuery(callbackData: String)(action: Context => Unit): Reaction =
    OnCallbackQuery(callbackData).build(action)

  /** A reaction that only gets executed if the update is a message that matches the given regular expression.
    *
    * @param regex  Regular expression that an incoming message must match for the action to be triggered.
    * @param action The function to be executed
    */
  def onMatch(regex: String)(action: Context => Unit): Reaction = OnMatch(regex).build(action)

  /** A reaction that gets executed when one or more users enter a chat.
    *
    * @param action The function to be executed
    */
  def onChatEnter(action: Context => Unit): Reaction = OnChatEnter().build(action)

  /** A reaction that gets executed when a user leaves a chat.
    *
    * @param action The function to be executed
    */
  def onChatLeave(action: Context => Unit): Reaction = OnChatLeave().build(action)

  /** A reaction that gets executed when a message is pinned.
    *
    * @param action The function to be executed
    */
  def onMessagePinned(action: Context => Unit): Reaction = OnMessagePinned().build(action)
}
