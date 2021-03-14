package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.payloads.ChatMembersAdded
import PPS19.scalagram.models.updates.MessageReceived

/** A reaction that gets executed when one or more users enter a chat. */
case class OnChatEnter() extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case MessageReceived(_, message) =>
            message.isInstanceOf[ChatMembersAdded]
          case _ => false
        }
      },
      action
    )
}
