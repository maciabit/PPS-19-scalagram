package PPS19.scalagram.logic

/** Any operation executed by the bot. */
trait Operation {

  /** Function to be executed.
    * @return true if the update processing should continue, false otherwise.
    */
  def operation: Context => Boolean
}

/** An operation that may or may not terminate the update processing.
  * Middlewares can be used for a lot of things, such as:
  * - to enrich the [[Context]] with additional data
  * - to log information about incoming updates
  * - to drop updates coming from unauthorized users
  *
  * @param operation Function to be executed.
  */
case class Middleware(operation: Context => Boolean) extends Operation

/** A trigger that, based on the bot's context, may return true or false.
  * Needed to create reactions ([[Reaction]])
  *
  * @param matches Function to be executed.
  */
case class Trigger(matches: Context => Boolean)

/** An operation that only executes if the given trigger passes.
  * If executed, it always terminates update processing.
  * Most of the bot's functionalities are implemented through reactions,
  * such as responding to a command or reacting to a user entering or leaving a chat.
  *
  * @param trigger Function that must return true for the action to be executed.
  * @param action  Function to be executed.
  */
case class Reaction(trigger: Trigger, action: Context => Unit) extends Operation {
  def operation: Context => Boolean =
    context =>
      if (trigger.matches(context)) {
        action(context)
        false
      } else {
        true
      }
}

/** A named operation that always returns false.
  * Steps are only used inside scenes ([[Scene]]).
  * The action of a step can use the following methods to set the active scene step for the next update:
  * - [[Context.nextStep()]] to go to the next scene step
  * - [[Context.goToStep()]] to go to a specific step by index or name
  * - [[Context.leaveScene()]] to exit the current scene
  *
  * @param name   Name of the step
  * @param action Function to be executed
  */
case class Step(name: String, action: Context => Unit) extends Operation {
  def operation: Context => Boolean =
    context => {
      action(context)
      false
    }
}
