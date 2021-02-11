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
            if (botContext.debug) {
              context.log.info("Update count: {}", botContext.updateCount)
              context.log.info("Update: {}", update)
            }

            botContext.update = Some(update)
            var continue = true

            // Execute middlewares
            for (
              middleware <- botContext.bot.middlewares.takeWhile(_ => continue)
            )
              continue = middleware.operation(botContext)

            // Check for matching reaction
            for (reaction <- botContext.bot.reactions.takeWhile(_ => continue))
              continue = reaction.operation(botContext)

            // Check for active scene
            botContext.activeScene match {
              case Some(scene) if continue =>
                continue = scene
                  .reactions(botContext.sceneStep.get)
                  .operation(botContext)
              case _ => continue = false
            }

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
