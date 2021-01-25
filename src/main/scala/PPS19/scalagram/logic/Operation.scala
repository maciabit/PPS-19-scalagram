package PPS19.scalagram.logic

/**
 * Any operation executed by the bot
 */
trait Operation {

  /**
   * @return true if the update processing should continue, false otherwise
   */
  def operation: () => Boolean

  def execute(): Boolean = operation()
}

/**
 * An operation that might terminate the update processing
 * @param operation: function to be executed
 */
case class Middleware(operation: () => Boolean) extends Operation

/**
 * An operation that always terminates update processing
 * @param action: function to be executed
 */
case class Reaction(action: () => Unit) extends Operation {
  def operation: () => Boolean = () => {
    action()
    false
  }
}
