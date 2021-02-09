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

case class Trigger(matches: Context => Boolean)

/**
  * An operation that always terminates update processing
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
