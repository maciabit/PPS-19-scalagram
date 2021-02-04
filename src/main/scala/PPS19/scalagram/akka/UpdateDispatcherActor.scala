package PPS19.scalagram.akka

import PPS19.scalagram.logic.{Bot, Context}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

import scala.concurrent.duration._

object UpdateDispatcherActor {

  // Fake updates
  private val updates = List(
    Update(100, "Message from Gianni"),
    Update(200, "Message from Met"),
    Update(300, "Message from Flavio"),
  )
  private def getUpdates = List(
    updates(scala.util.Random.nextInt(updates.length)),
    updates(scala.util.Random.nextInt(updates.length))
  )

  def apply(bot: Bot, interval: FiniteDuration, workerTimeout: FiniteDuration): Behavior[LookForUpdates] =
    scheduleUpdateBehavior(bot, interval, workerTimeout)

  private def scheduleUpdateBehavior(
    bot: Bot,
    interval: FiniteDuration,
    workerTimeout: FiniteDuration
  ): Behavior[LookForUpdates] =
    Behaviors.withTimers { timers =>
      timers.startTimerAtFixedRate(LookForUpdates(), interval)
      fetchUpdateBehavior(bot, workerTimeout)
    }

  private def fetchUpdateBehavior(bot: Bot, workerTimeout: FiniteDuration): Behavior[LookForUpdates] =
    Behaviors.receive { (context, _) =>
      context.log.info("Fetching updates")
      for (update <- getUpdates) {
        val workerName = s"worker${update.chatId}"
        val worker = context.child(workerName) match {
          case Some(actor) => actor.asInstanceOf[ActorRef[WorkerMessage]]
          case None =>
            val botContext = Context(bot)
            botContext.timeout = workerTimeout
            context.spawn(WorkerActor(botContext), workerName)
        }
        worker ! ProcessUpdate(update)
      }
      Behaviors.same
    }
}
