package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.MessageUpdate
import PPS19.scalagram.models.messages.TextMessage

case class OnMatch(string: String) extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(MessageUpdate(_, message)) if message.isInstanceOf[TextMessage] =>
            string.r.matches(message.asInstanceOf[TextMessage].text)
          case _ => false
        }
      },
      action
    )
}
