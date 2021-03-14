package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.payloads.TextMessage
import PPS19.scalagram.models.updates.{MessageUpdate, UpdateType}

/** A reaction that only gets executed if the update is an edited message equal to one of the given strings.
  *
  * @param strings Messages that allow the action to be triggered.
  *                If no messages are passed, the action will be triggered by any edited text message.
  */
case class OnMessageEdited(strings: String*) extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case update: MessageUpdate
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
