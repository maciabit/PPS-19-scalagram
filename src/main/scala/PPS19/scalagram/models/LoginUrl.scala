package PPS19.scalagram.models

/** Represents an HTTP URL used to automatically authorize the user with Telegram on a website which implements the Telegram Login Widget.
  *
  * @param url                An HTTP URL to be opened with user authorization data added to the query string when the button is pressed
  * @param forwardText        (Optional) New text of the button in forwarded messages.
  * @param botUsername        (Optional) Username of a bot, which will be used for user authorization.
  * @param requestWriteAccess (Optional) If true, the permission for the bot to send message to the user will be requested.
  */
final case class LoginUrl(
    url: String,
    forwardText: Option[String] = None,
    botUsername: Option[String] = None,
    requestWriteAccess: Option[Boolean] = None
)
