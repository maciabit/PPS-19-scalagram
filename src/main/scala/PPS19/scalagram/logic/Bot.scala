package PPS19.scalagram.logic

import PPS19.scalagram.logic.reactions._
import PPS19.scalagram.methods._
import PPS19.scalagram.models.messages.TelegramMessage
import PPS19.scalagram.models.{ChatId, InputFile, ReplyMarkup, Update}
import PPS19.scalagram.modes.polling.Mode

import scala.util.Try

case class BotToken(token: String) {
  def get: String = token
}

sealed trait Bot {
  val token: BotToken
  val middlewares: List[Middleware]
  val reactions: List[Reaction]
  val scenes: List[Scene]

  def launch(mode: Mode): Unit = mode.start(this)

  def getUpdates(
      offset: Option[Long] = None,
      limit: Option[Int] = None,
      timeout: Option[Int] = None,
      allowedUpdates: Option[Array[String]] = None
  ): Try[List[Update]] =
    GetUpdates(token, offset, limit, timeout, allowedUpdates).call()

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

  def deleteMessage(chatId: ChatId, messageId: Int): Try[Boolean] =
    DeleteMessage(token, chatId, messageId).call()

  def pinMessage(
      chatId: ChatId,
      messageId: Int,
      disableNotification: Option[Boolean]
  ): Try[Boolean] =
    PinMessage(token, chatId, messageId, disableNotification).call()

  def unpinMessage(chatId: ChatId, messageId: Int): Try[Boolean] =
    UnpinMessage(token, chatId, messageId).call()

  def unpinAllMessages(chatId: ChatId): Try[Boolean] =
    UnpinAllMessages(token, chatId).call()

  def answerCallbackQuery(
      callbackQueryId: String,
      text: Option[String],
      showAlert: Option[Boolean],
      url: Option[String],
      cacheTime: Option[Int]
  ): Unit =
    AnswerCallbackQuery(token, callbackQueryId, text, showAlert, url, cacheTime)
      .call()
}

object Bot {

  def apply(
      token: BotToken,
      middlewares: List[Middleware] = List.empty,
      reactions: List[Reaction] = List.empty,
      scenes: List[Scene] = List.empty
  ): Bot = BotImpl(token, middlewares, scenes, reactions)

  def unapply(
      bot: Bot
  ): Option[(BotToken, List[Middleware], List[Scene], List[Reaction])] =
    Some(bot.token, bot.middlewares, bot.scenes, bot.reactions)

  case class BotImpl(
      token: BotToken,
      middlewares: List[Middleware],
      scenes: List[Scene],
      reactions: List[Reaction]
  ) extends Bot

  def onMessage(texts: String*)(action: Context => Unit): Reaction =
    OnMessage(texts: _*).build(action)

  def onStart(action: Context => Unit): Reaction = OnStart().build(action)

  def onHelp(action: Context => Unit): Reaction = OnHelp().build(action)

  def onMessageEdited(texts: String*)(action: Context => Unit): Reaction =
    OnMessageEdited(texts: _*).build(action)

  def onCallbackQuery(query: String)(action: Context => Unit): Reaction =
    OnCallbackQuery(query).build(action)

  def onMatch(regex: String)(action: Context => Unit): Reaction =
    OnMatch(regex).build(action)

  def onChatEnter(action: Context => Unit): Reaction =
    OnChatEnter().build(action)

  def onChatLeave(action: Context => Unit): Reaction =
    OnChatLeave().build(action)

  def onMessagePinned(action: Context => Unit): Reaction =
    OnMessagePinned().build(action)
}
