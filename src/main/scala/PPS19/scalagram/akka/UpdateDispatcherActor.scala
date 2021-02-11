package PPS19.scalagram.akka

import PPS19.scalagram.logic.{Bot, Context}
import PPS19.scalagram.models.MessageUpdate
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

import scala.concurrent.duration._

object UpdateDispatcherActor {

  def apply(
      bot: Bot,
      interval: FiniteDuration,
      workerTimeout: FiniteDuration,
      debug: Boolean = false
  ): Behavior[LookForUpdates] =
    scheduleUpdateBehavior(bot, interval, workerTimeout, debug)

  private def scheduleUpdateBehavior(
      bot: Bot,
      interval: FiniteDuration,
      workerTimeout: FiniteDuration,
      debug: Boolean
  ): Behavior[LookForUpdates] =
    Behaviors.withTimers { timers =>
      timers.startTimerAtFixedRate(LookForUpdates(), interval)
      fetchUpdateBehavior(bot, workerTimeout, debug = debug)
    }

  private def fetchUpdateBehavior(
      bot: Bot,
      workerTimeout: FiniteDuration,
      nextUpdateId: Option[Long] = None,
      debug: Boolean
  ): Behavior[LookForUpdates] =
    Behaviors.receive { (context, _) =>
      if (debug) context.log.info("Fetching updates")
      // Fetch updates
      val updates = bot.getUpdates(nextUpdateId)
      // Dispatch updates to workers
      for (update <- updates.getOrElse(List.empty)) {
        val chatId = update.asInstanceOf[MessageUpdate].message.chat.id
        val workerName = s"worker$chatId"
        val worker = context.child(workerName) match {
          case Some(actor) => actor.asInstanceOf[ActorRef[WorkerMessage]]
          case None =>
            val botContext = Context(bot, debug)
            botContext.timeout = workerTimeout
            context.spawn(WorkerActor(botContext), workerName)
        }
        worker ! ProcessUpdate(update)
      }
      val updateId =
        if (updates.isFailure || updates.get.isEmpty) nextUpdateId
        else Some(updates.get.last.updateId + 1)
      fetchUpdateBehavior(bot, workerTimeout, updateId, debug)
    }
}
