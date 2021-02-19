package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.{ChannelPostEdited, MessageEdited}
import PPS19.scalagram.models.messages.TextMessage

case class OnMessageEdited(strings: String*) extends VarArgReactionBuilder {

  override def fromStrings(strings: String*): OnMessageEdited = OnMessageEdited(strings:_*)

  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageEdited(_, editedMessage))
            if editedMessage.isInstanceOf[TextMessage] =>
            strings.isEmpty || strings.contains(
              editedMessage.asInstanceOf[TextMessage].text
            )
          case Some(ChannelPostEdited(_, editedChannelPost))
            if editedChannelPost.isInstanceOf[TextMessage] =>
            strings.isEmpty || strings.contains(
              editedChannelPost.asInstanceOf[TextMessage].text
            )
          case _ => false
        }
      },
      action
    )

}
