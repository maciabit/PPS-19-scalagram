package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.MessageReceived
import PPS19.scalagram.models.messages.MessagePinned

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
