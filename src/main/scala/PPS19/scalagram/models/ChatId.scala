package PPS19.scalagram.models

class ChatId private (chatId: Either[String, Int]) {
  def get: Any = chatId.fold(l => l, r => r)
}

object ChatId {
  def apply(chatId: String) = new ChatId(Left(chatId))
  def apply(chatId: Int) = new ChatId(Right(chatId))
}