package PPS19.scalagram.akka

import PPS19.scalagram.logic.Context
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import java.time.LocalDateTime

object WorkerActor {

  def apply(context: Context): Behavior[WorkerMessage] =
    receiveBehavior(context)

  def receiveBehavior(botContext: Context): Behavior[WorkerMessage] = {
    Behaviors.withTimers { timers =>
      Behaviors.receive { (context, message) =>
        message match {
          case ProcessUpdate(update) =>
            botContext.updateCount += 1
            botContext.lastUpdateTimestamp = LocalDateTime.now()
            timers.startSingleTimer(
              Timeout(botContext.lastUpdateTimestamp),
              botContext.timeout
            )
            context.log.info("Update count: {}", botContext.updateCount)
            context.log.info("Update: {}", update)
            receiveBehavior(botContext)
          case Timeout(messageTimestamp)
              if messageTimestamp != botContext.lastUpdateTimestamp =>
            context.log.info("Timer {}", messageTimestamp)
            receiveBehavior(botContext)
          case _ =>
            context.log.info("Terminating after timeout...")
            Behaviors.stopped
        }
      }
    }
  }
}
