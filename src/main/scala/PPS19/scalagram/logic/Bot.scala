package PPS19.scalagram.logic

import PPS19.scalagram.methods._
import PPS19.scalagram.models.messages.{
  ChatMemberRemoved,
  ChatMembersAdded,
  MessagePinned,
  TelegramMessage,
  TextMessage
}
import PPS19.scalagram.models.{
  CallbackButtonSelected,
  ChannelPost,
  ChannelPostEdited,
  ChatId,
  InputFile,
  MessageEdited,
  MessageReceived,
  MessageUpdate,
  ReplyMarkup,
  Update
}
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

  def onMessage(texts: String*)(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message))
              if message.isInstanceOf[TextMessage] =>
            texts.isEmpty || texts.contains(
              message.asInstanceOf[TextMessage].text
            )
          case Some(ChannelPost(_, channelPost))
              if channelPost.isInstanceOf[TextMessage] =>
            texts.isEmpty || texts.contains(
              channelPost.asInstanceOf[TextMessage].text
            )
          case _ => false
        }
      },
      action
    )
  }

  def onStart(action: Context => Unit): Reaction = onMessage("/start")(action)

  def onHelp(action: Context => Unit): Reaction = onMessage("/help")(action)

  def onMessageEdited(texts: String*)(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageEdited(_, editedMessage))
              if editedMessage.isInstanceOf[TextMessage] =>
            texts.isEmpty || texts.contains(
              editedMessage.asInstanceOf[TextMessage].text
            )
          case Some(ChannelPostEdited(_, editedChannelPost))
              if editedChannelPost.isInstanceOf[TextMessage] =>
            texts.isEmpty || texts.contains(
              editedChannelPost.asInstanceOf[TextMessage].text
            )
          case _ => false
        }
      },
      action
    )
  }

  def onCallbackQuery(query: String)(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(CallbackButtonSelected(_, callbackQuery))
              if callbackQuery.message.orNull.isInstanceOf[TextMessage] =>
            callbackQuery.message.get.asInstanceOf[TextMessage].text == query
          case _ => false
        }
      },
      action
    )
  }

  def onMatch(regex: String)(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageUpdate(_, message))
              if message.isInstanceOf[TextMessage] =>
            regex.r.matches(message.asInstanceOf[TextMessage].text)
          case _ => false
        }
      },
      action
    )
  }

  def onChatEnter(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message)) =>
            message.isInstanceOf[ChatMembersAdded]
          case _ => false
        }
      },
      action
    )
  }

  def onChatLeave(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message)) =>
            message.isInstanceOf[ChatMemberRemoved]
          case _ => false
        }
      },
      action
    )
  }

  def onMessagePinned(action: Context => Unit): Reaction = {
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message)) =>
            message.isInstanceOf[MessagePinned]
          case _ => false
        }
      },
      action
    )
  }
}
