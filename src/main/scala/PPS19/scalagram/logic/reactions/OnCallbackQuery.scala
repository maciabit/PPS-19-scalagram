package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.CallbackButtonSelected
import PPS19.scalagram.models.messages.TextMessage

case class OnCallbackQuery(string: String) extends SingleArgReactionBuilder {

  override def fromString(string: String): OnCallbackQuery = OnCallbackQuery(string)

  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(CallbackButtonSelected(_, callbackQuery))
            if callbackQuery.message.orNull.isInstanceOf[TextMessage] =>
            callbackQuery.message.get.asInstanceOf[TextMessage].text == string
          case _ => false
        }
      },
      action
    )

}
