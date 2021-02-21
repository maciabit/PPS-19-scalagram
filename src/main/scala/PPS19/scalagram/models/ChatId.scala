package PPS19.scalagram.models

class ChatId private (chatId: Either[String, Long]) {
  def get: Any = chatId.fold(l => l, r => r)
}

object ChatId {
  def apply(chatId: String) = new ChatId(Left(chatId))
  def apply(chatId: Long) = new ChatId(Right(chatId))
}
