package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.messages.TextMessage
import PPS19.scalagram.models.{MessageUpdate, UpdateType}

case class OnMessage(strings: String*) extends VarArgReactionBuilder {

  override def fromStrings(strings: String*): OnMessage = OnMessage(strings: _*)

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
                message.replace(s"@${context.bot.user.get.username}", "").trim
              else
                message
            strings.isEmpty || strings.contains(stringToMatch)
          case _ => false
        }
      },
      action
    )
}
