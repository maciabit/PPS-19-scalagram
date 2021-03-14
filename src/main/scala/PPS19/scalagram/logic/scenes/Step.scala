package PPS19.scalagram.logic.scenes

import PPS19.scalagram.logic.reactions.OnMessage
import PPS19.scalagram.logic.{Context, Operation, Trigger}

/** A named operation that always returns false.
  * Steps are only used inside scenes ([[Scene]]).
  * The action of a step can use the following methods to set the active scene step for the next update:
  * - [[Context.nextStep]] to go to the next scene step
  * - [[Context.goToStep(index:Int):Unit*]] to go to a specific step by index
  * - [[Context.goToStep(stepName:String):Unit*]] to go to a specific step by name
  * - [[Context.leaveScene]] to exit the current scene
  *
  * @param name   Name of the step
  * @param action Function to be executed
  */
case class Step(name: String, action: Context => Unit) extends Operation {

  val trigger: Trigger = Trigger { context =>
    val onMessage = OnMessage().build(_ => {})
    onMessage.trigger.matches(context)
  }

  def operation: Context => Boolean =
    context =>
      if (trigger.matches(context)) {
        action(context)
        false
      } else {
        true
      }
}
