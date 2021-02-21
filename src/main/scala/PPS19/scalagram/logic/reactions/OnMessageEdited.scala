package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.messages.TextMessage
import PPS19.scalagram.models.{MessageUpdate, UpdateType}

case class OnMessageEdited(strings: String*) extends VarArgReactionBuilder {

  override def fromStrings(strings: String*): OnMessageEdited = OnMessageEdited(strings:_*)

  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(update: MessageUpdate)
            if update.updateType == UpdateType.MessageEdited ||
              update.updateType == UpdateType.ChannelPostEdited =>
            strings.isEmpty || strings.contains(
              update.message.asInstanceOf[TextMessage].text
            )
          case _ => false
        }
      },
      action
    )
}
