package PPS19.scalagram.logic

/**
  * Any operation executed by the bot
  */
trait Operation {

  /**
    * @return true if the update processing should continue, false otherwise
    */
  def operation: Context => Boolean
}

/**
  * An operation that might terminate the update processing
  * @param operation: function to be executed
  */
case class Middleware(operation: Context => Boolean) extends Operation

/**
  * A trigger that, based on the bot's context, may return true or false
  * @param matches: function to be executed
  */
case class Trigger(matches: Context => Boolean)

/**
  * An operation that only executes if the given trigger passes.
  * If executed, it always terminates update processing
  * @param trigger: function to pass for the action to be executed
  * @param action: function to be executed
  */
case class Reaction(trigger: Trigger, action: Context => Unit)
    extends Operation {
  def operation: Context => Boolean =
    context =>
      if (trigger.matches(context)) {
        action(context)
        false
      } else {
        true
      }
}

/**
  * An operation that always returns false.
  * Used inside Scenes.
  * @param action: function to be executed
  */
case class Step(name: String, action: Context => Unit) extends Operation {
  def operation: Context => Boolean =
    context => {
      action(context)
      false
    }
}
