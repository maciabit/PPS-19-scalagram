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

  /**
    * Start polling
    * @param pollingInterval   Interval at which updates have to be requested (default: 300ms)
    * @param timeoutDelay      Delay after which a bot, if it has not received updates, forgets the status of the
    *                          interaction on a certain chat (default: 1 day)
    */
  def startPolling(
      bot: Bot,
      pollingInterval: FiniteDuration = Polling.defaultPollingInterval,
      timeoutDelay: FiniteDuration = Polling.defaultTimeoutDelay
  ): Unit
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

    override def start(bot: Bot): Unit =
      startPolling(bot, pollingInterval, timeoutDelay)

    override def startPolling(
        bot: Bot,
        pollingInterval: FiniteDuration,
        timeoutDelay: FiniteDuration
    ): Unit = {
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
