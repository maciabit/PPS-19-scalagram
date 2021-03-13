package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.payloads.MessagePinned
import PPS19.scalagram.models.updates.MessageReceived

/** A reaction that gets executed when a message is pinned. */
case class OnMessagePinned() extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageReceived(_, message)) =>
            message.isInstanceOf[MessagePinned]
          case _ => false
        }
      },
      action
    )
}
