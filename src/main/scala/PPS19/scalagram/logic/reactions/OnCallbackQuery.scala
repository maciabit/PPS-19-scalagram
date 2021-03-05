package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.CallbackButtonSelected

case class OnCallbackQuery(string: String) extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case Some(CallbackButtonSelected(_, callbackQuery)) =>
            callbackQuery.data.contains(string)
          case _ => false
        }
      },
      action
    )
}
