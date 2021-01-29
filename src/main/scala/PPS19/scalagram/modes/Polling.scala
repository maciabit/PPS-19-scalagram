package PPS19.scalagram.modes

import PPS19.scalagram.akka.{LookForUpdates, UpdateDispatcherActor}
import akka.actor.typed.ActorSystem

import scala.concurrent.duration._

/** Polling mode for Telegram bots update retrieval */
sealed trait Polling {

  /**
   * Start polling
   * @param pollingInterval   Interval at which updates have to be requested (default: 300ms)
   * @param timeoutDelay      Delay after which a bot, if it has not received updates, forgets the status of the
   *                          interaction on a certain chat (default: 1 day)
   */
  def start(pollingInterval: FiniteDuration = 300.milliseconds, timeoutDelay: FiniteDuration = 1.days): Unit
}

object Polling {

  def apply(): Polling = PollingImpl()

  case class PollingImpl() extends Polling {
    override def start(pollingInterval: FiniteDuration, timeoutDelay: FiniteDuration): Unit = {
      // Create the actor system with an UpdateDispatcherActor as guardian
      val system = ActorSystem(UpdateDispatcherActor(pollingInterval, timeoutDelay), "dispatcher")
      // Send a LookForUpdates message to UpdateDispatcherActor to trigger update polling
      system ! LookForUpdates()
    }
  }

}

object TryPolling extends App {
  val polling = Polling()
  polling.start(5.seconds, 10.seconds)
}