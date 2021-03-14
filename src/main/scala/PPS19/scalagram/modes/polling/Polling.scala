package PPS19.scalagram.modes.polling

<<<<<<< HEAD
import PPS19.scalagram.logic.Bot
=======
import PPS19.scalagram.logic.Scalagram
>>>>>>> develop
import PPS19.scalagram.modes.polling.actorsystem.{LookForUpdates, UpdateDispatcherActor}
import akka.actor.typed.ActorSystem

import scala.concurrent.duration._

trait Mode {
  def start(bot: Scalagram): Unit
}

/** Polling mode for Telegram bots update retrieval */
sealed trait Polling extends Mode {
  val pollingInterval: FiniteDuration
  val timeoutDelay: FiniteDuration
}

object Polling {

  final val defaultPollingInterval: FiniteDuration = 300.milliseconds
  final val defaultTimeoutDelay: FiniteDuration = 1.days

  def apply(
      pollingInterval: FiniteDuration = Polling.defaultPollingInterval,
      timeoutDelay: FiniteDuration = Polling.defaultTimeoutDelay
  ): Polling = PollingImpl(pollingInterval, timeoutDelay)

  case class PollingImpl(
      pollingInterval: FiniteDuration,
      timeoutDelay: FiniteDuration
  ) extends Polling {

    override def start(bot: Scalagram): Unit = {
      // Create the actor system with an UpdateDispatcherActor as guardian
      val system = ActorSystem(
        UpdateDispatcherActor(bot, pollingInterval, timeoutDelay),
        "dispatcher"
      )
      // Send a LookForUpdates message to UpdateDispatcherActor to trigger update polling
      system ! LookForUpdates()
    }
  }
}
