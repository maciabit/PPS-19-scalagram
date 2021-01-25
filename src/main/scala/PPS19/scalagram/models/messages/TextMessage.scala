package PPS19.scalagram.models.messages
import PPS19.scalagram.models.{Chat, MessageEntity, User}

case class TextMessage(messageId: Int,
                             chat: Chat,
                             date: Int,
                             text: String,
                             senderChat : Option[Chat] = None,
                             authorSignature: Option[String] = None,
                             forwardFromMessageId: Option[Int] = None,
                             forwardSignature: Option[String] = None,
                             forwardSenderName: Option[String] = None,
                             forwardDate: Option[Int] = None,
                             editDate: Option[Int] = None,
                              /*
                             //entities: Option[List[MessageEntity]] = None,

                             //from: Option[User] = None,

                             //forwardFrom: Option[User] = None,
                             forwardFromChat: Option[Chat] = None,

                             replyToMessage: Option[TelegramMessage] = None,


                             viaBot: Option[User] = None,
                             */
                      )
  extends UserMessage
