package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.messages.TextMessage
import PPS19.scalagram.models.{MessageUpdate, UpdateType}

/** A reaction that only gets executed if the update is a message equal to one of the given strings.
  *
  * @param strings Messages that allow the action to be triggered.
  *                If no messages are passed, the action will be triggered by any text message.
  */
case class OnMessage(strings: String*) extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(update: MessageUpdate)
              if update.updateType == UpdateType.MessageReceived ||
                update.updateType == UpdateType.ChannelPostReceived =>
            val message = update.message.asInstanceOf[TextMessage].text
            val stringToMatch =
              if (message.startsWith("/") && context.bot.user.isDefined)
                message.replace(s"@${context.bot.user.get.username.get}", "").trim
              else
                message
            strings.isEmpty || strings.contains(stringToMatch)
          case _ => false
        }
      },
      action
    )
}
