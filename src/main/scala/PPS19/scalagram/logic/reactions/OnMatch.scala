package PPS19.scalagram.logic.reactions
import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.payloads.TextMessage
import PPS19.scalagram.models.updates.MessageUpdate

/** A reaction that only gets executed if the update is a message that matches the given regular expression.
  *
  * @param regex Regular expression that an incoming message must match for the action to be triggered.
  */
case class OnMatch(regex: String) extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case MessageUpdate(_, message) if message.isInstanceOf[TextMessage] =>
            regex.r.matches(message.asInstanceOf[TextMessage].text)
          case _ => false
        }
      },
      action
    )
}
