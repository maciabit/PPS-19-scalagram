package PPS19.scalagram.dsl.keyboard

import PPS19.scalagram.dsl.reactions.ReactionUtils.MessageContainer

object KeyboardConversions {
  implicit def stringToMessageContainer(string: String): MessageContainer =
    MessageContainer(string, None, None)

  implicit def stringToButtonContainer(
      string: String
  ): KeyboardButtonContainer =
    KeyboardButtonContainer(string, callbackData = Some(string))

  implicit def stringToButtonRow(string: String): KeyboardRow =
    KeyboardRowImpl(
      Seq(KeyboardButtonContainer(string, callbackData = Some(string)))
    )

  implicit def buttonContainerToButtonRow(
      buttonContainer: KeyboardButtonContainer
  ): KeyboardRow =
    KeyboardRowImpl(Seq(buttonContainer))
}
