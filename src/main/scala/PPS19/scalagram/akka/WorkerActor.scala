package PPS19.scalagram.akka

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import java.time.LocalDateTime
import scala.concurrent.duration.FiniteDuration

object WorkerActor {

  def apply(timeout: FiniteDuration): Behavior[WorkerMessage] =
    receiveBehavior(timeout, 0, LocalDateTime.now())

  def receiveBehavior(
    timeout: FiniteDuration,
    updateCount: Int,
    lastUpdateTimestamp: LocalDateTime
  ): Behavior[WorkerMessage] = {
    Behaviors.withTimers { timers =>
      Behaviors.receive { (context, message) =>
        message match {
          case ProcessUpdate(update) =>
            val newCount = updateCount + 1
            val newTimestamp = LocalDateTime.now()
            timers.startSingleTimer(Timeout(newTimestamp), timeout)
            context.log.info("Update {} Update count: {}", update, newCount)
            receiveBehavior(timeout, newCount, newTimestamp)
          case Timeout(messageTimestamp) if messageTimestamp != lastUpdateTimestamp =>
            context.log.info("Timer {}", messageTimestamp)
            receiveBehavior(timeout, updateCount, lastUpdateTimestamp)
          case _ =>
            context.log.info("Terminating after timeout...")
            Behaviors.stopped
        }
      }
    }
  }
}