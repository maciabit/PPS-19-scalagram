package PPS19.scalagram.modes

import PPS19.scalagram.akka.{LookForUpdates, UpdateDispatcherActor}
import PPS19.scalagram.logic.Bot
import akka.actor.typed.ActorSystem

import scala.concurrent.duration._

trait Mode {
  def start(bot: Bot): Unit
}

/** Polling mode for Telegram bots update retrieval */
sealed trait Polling extends Mode {
  val pollingInterval: FiniteDuration
  val timeoutDelay: FiniteDuration
  val debug: Boolean
}

object Polling {

  final val defaultPollingInterval: FiniteDuration = 300.milliseconds
  final val defaultTimeoutDelay: FiniteDuration = 1.days

  def apply(
      pollingInterval: FiniteDuration = Polling.defaultPollingInterval,
      timeoutDelay: FiniteDuration = Polling.defaultTimeoutDelay,
      debug: Boolean = false
  ): Polling = PollingImpl(pollingInterval, timeoutDelay, debug)

  case class PollingImpl(
      pollingInterval: FiniteDuration,
      timeoutDelay: FiniteDuration,
      debug: Boolean
  ) extends Polling {

    override def start(bot: Bot): Unit = {
      // Create the actor system with an UpdateDispatcherActor as guardian
      val system = ActorSystem(
        UpdateDispatcherActor(bot, pollingInterval, timeoutDelay, debug),
        "dispatcher"
      )
      // Send a LookForUpdates message to UpdateDispatcherActor to trigger update polling
      system ! LookForUpdates()
    }
  }
}
