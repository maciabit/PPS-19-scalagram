package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.MessageReceived
import PPS19.scalagram.models.messages.ChatMembersAdded

/** A reaction that gets executed when one or more users enter a chat. */
case class OnChatEnter() extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message)) =>
            message.isInstanceOf[ChatMembersAdded]
          case _ => false
        }
      },
      action
    )
}
