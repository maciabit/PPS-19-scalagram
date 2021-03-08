package PPS19.scalagram.models

/** The token of a bot.
  *
  * @param token Token string
  */
case class BotToken(token: String) {
  def get: String = token
}
