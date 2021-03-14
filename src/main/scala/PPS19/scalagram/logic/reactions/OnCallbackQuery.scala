package PPS19.scalagram.logic.reactions

import PPS19.scalagram.logic.{Context, Reaction, Trigger}
import PPS19.scalagram.models.updates.CallbackButtonSelected

/** A reaction that only gets executed if the update is a callback query with the data property equal to the given string.
  *
  * @param callbackData Callback data that allows the action to be triggered.
  */
case class OnCallbackQuery(callbackData: String) extends ReactionBuilder {
  override def build(action: Context => Unit): Reaction =
    Reaction(
      Trigger { context =>
        context.update match {
          case CallbackButtonSelected(_, callbackQuery) => callbackQuery.data.contains(callbackData)
          case _                                        => false
        }
      },
      action
    )
}
