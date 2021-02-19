package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.{ChannelPost, MessageReceived}
import PPS19.scalagram.models.messages.TextMessage

case class OnMessage(strings: String*) extends VarArgReactionBuilder {

  override def fromStrings(strings: String*): OnMessage = OnMessage(strings:_*)

  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message))
            if message.isInstanceOf[TextMessage] =>
            strings.isEmpty || strings.contains(
              message.asInstanceOf[TextMessage].text
            )
          case Some(ChannelPost(_, channelPost))
            if channelPost.isInstanceOf[TextMessage] =>
            strings.isEmpty || strings.contains(
              channelPost.asInstanceOf[TextMessage].text
            )
          case _ => false
        }
      },
      action
    )
}
