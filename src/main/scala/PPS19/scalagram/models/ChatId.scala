package PPS19.scalagram.models

/** Unique identifier for a target chat or username of the target channel (in the format @channelusername)
  *
  * @param chatId Unique identifier for a target chat or username of the target channel (in the format @channelusername)
  */
class ChatId private (chatId: Either[String, Long]) {
  def get: Any = chatId.fold(l => l, r => r)
}

/** Companion object for ChatId. Used as a Factory. */
object ChatId {

  /** Creates a ChatId with the given string representing the username of the target channel.
    *
    * @param chatId The username of the target channel.
    * @return the ChatId created.
    */
  def apply(chatId: String) = new ChatId(Left(chatId))

  /** Creates a ChatId with the given unique identifier of the chat.
    *
    * @param chatId The unique identifier of the chat.
    * @return the ChatId created.
    */
  def apply(chatId: Long) = new ChatId(Right(chatId))
}
