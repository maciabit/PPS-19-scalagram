package PPS19.scalagram.models.messages
import PPS19.scalagram.models._

final case class PhotoMessage(
    messageId: Int,
    chat: Chat,
    date: Int,
    photo: List[PhotoSize],
    caption: Option[String] = None,
    captionEntities: Option[List[MessageEntity]] = None,
    from: Option[User] = None,
    forwardFrom: Option[User] = None,
    forwardFromChat: Option[Chat] = None,
    forwardFromMessageId: Option[Int] = None,
    forwardSignature: Option[String] = None,
    forwardSenderName: Option[String] = None,
    forwardDate: Option[Int] = None,
    replyToMessage: Option[TelegramMessage] = None,
    editDate: Option[Int] = None,
    authorSignature: Option[String] = None,
    viaBot: Option[User] = None
) extends UserMessage
