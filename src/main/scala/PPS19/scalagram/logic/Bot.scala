package PPS19.scalagram.logic

import PPS19.scalagram.methods.{AnswerCallbackQuery, DeleteMessage, GetNewUpdates, PinMessage, SendMessage, SendPhoto, UnpinAllMessages, UnpinMessage}
import PPS19.scalagram.models.{InputFile, MessageUpdate, ReplyMarkup, Update}
import PPS19.scalagram.models.messages.{TelegramMessage, TextMessage}
import PPS19.scalagram.modes.Mode

import scala.util.Try

case class BotToken(token: String)

sealed trait Bot {
  val token: BotToken
  val middlewares: List[Middleware]
  val scenes: List[Scene]
  val reactions: List[Reaction]

  def onCommand(command: String)(action: Context => Unit): Reaction = {
    Reaction(
      Trigger {
        case MessageUpdate(_, message) if message.isInstanceOf[TextMessage] =>
          message.asInstanceOf[TextMessage].text == command
        case _ => false
      },
      action
    )
  }

  def launch(mode: Mode): Unit = mode.start(this)

  def getUpdates(
    offset: Option[Int] = None,
    limit: Option[Int] = None,
    timeout: Option[Int] = None,
    allowedUpdates: Option[Array[String]] = None
  ): Try[List[Update]] = GetNewUpdates().call(offset, limit, timeout, allowedUpdates)

  def sendMessage(
    chatId: Either[String, Int],
    text: String,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disablePreview: Option[Boolean] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage] = SendMessage().call(
    chatId,
    text,
    parseMode,
    entities,
    disablePreview,
    disableNotification,
    replyToMessageId,
    allowSendingWithoutReply,
    replyMarkup
  )

  def sendPhoto(
    chatId: Either[String, Int],
    photo: InputFile,
    caption: Option[String] = None,
    parseMode: Option[String] = None,
    entities: Option[Vector[Any]] = None,
    disableNotification: Option[Boolean] = None,
    replyToMessageId: Option[Int] = None,
    allowSendingWithoutReply: Option[Boolean] = None,
    replyMarkup: Option[ReplyMarkup] = None
  ): Try[TelegramMessage] = SendPhoto().call(
    chatId,
    photo,
    caption,
    parseMode,
    entities,
    disableNotification,
    replyToMessageId,
    allowSendingWithoutReply,
    replyMarkup
  )

  def deleteMessage(chatId: Either[String, Int], messageId: Int): Try[Boolean] =
    DeleteMessage().call(chatId, messageId)

  def pinMessage(chatId: Either[String, Int], messageId: Int, disableNotification: Option[Boolean]): Try[Boolean] =
    PinMessage().call(chatId, messageId, disableNotification)

  def unpinMessage(chatId: Either[String, Int], messageId: Int): Try[Boolean] =
    UnpinMessage().call(chatId, messageId)

  def unpinAllMessages(chatId: Either[String, Int]): Try[Boolean] = UnpinAllMessages().call(chatId)

  def answerCallbackQuery(
    callbackQueryId: String,
    text: Option[String],
    showAlert: Option[Boolean],
    url: Option[String],
    cacheTime: Option[Int]
  ): Unit = AnswerCallbackQuery().call(callbackQueryId, text, showAlert, url, cacheTime)
}

object Bot {

  def apply(
    token: BotToken,
    middlewares: List[Middleware] = List.empty,
    scenes: List[Scene] = List.empty,
    reactions: List[Reaction] = List.empty
  ): Bot = BotImpl(token, middlewares, scenes, reactions)

  def unapply(bot: Bot): Option[(BotToken, List[Middleware], List[Scene], List[Reaction])] =
    Some(bot.token, bot.middlewares, bot.scenes, bot.reactions)

  case class BotImpl (
    token: BotToken,
    middlewares: List[Middleware],
    scenes: List[Scene],
    reactions: List[Reaction]
  ) extends Bot
}