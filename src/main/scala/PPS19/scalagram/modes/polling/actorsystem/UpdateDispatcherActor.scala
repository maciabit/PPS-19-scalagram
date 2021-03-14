package PPS19.scalagram.modes.polling.actorsystem

import PPS19.scalagram.logic.{Context, Scalagram}
import PPS19.scalagram.models.updates.{CallbackButtonSelected, MessageUpdate}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

import scala.concurrent.duration._

object UpdateDispatcherActor {

  def apply(
      bot: Scalagram,
      interval: FiniteDuration,
      workerTimeout: FiniteDuration
  ): Behavior[LookForUpdates] =
    scheduleUpdateBehavior(bot, interval, workerTimeout)

  private def scheduleUpdateBehavior(
      bot: Scalagram,
      interval: FiniteDuration,
      workerTimeout: FiniteDuration
  ): Behavior[LookForUpdates] =
    Behaviors.withTimers { timers =>
      timers.startTimerAtFixedRate(LookForUpdates(), interval)
      fetchUpdateBehavior(bot, workerTimeout)
    }

  private def fetchUpdateBehavior(
      bot: Scalagram,
      workerTimeout: FiniteDuration,
      nextUpdateId: Option[Long] = None
  ): Behavior[LookForUpdates] =
    Behaviors.receive { (context, _) =>
      // Fetch updates
      val updates = bot.getUpdates(nextUpdateId)
      // Dispatch updates to workers
      for (update <- updates.getOrElse(List.empty)) {
        val chatId = update match {
          case MessageUpdate(_, message) => Some(message.chat.id)
          case CallbackButtonSelected(_, callbackQuery) if callbackQuery.message.isDefined =>
            Some(callbackQuery.message.get.chat.id)
          case _ => None
        }
        if (chatId.isDefined) {
          val workerName = s"worker${chatId.get}"
          val worker = context.child(workerName) match {
            case Some(actor) => actor.asInstanceOf[ActorRef[WorkerMessage]]
            case None =>
              val botContext = Context(bot)
              botContext.timeout = workerTimeout
              context.spawn(WorkerActor(botContext), workerName)
          }
          worker ! ProcessUpdate(update)
        }
      }
      val updateId =
        if (updates.isFailure || updates.get.isEmpty) nextUpdateId
        else Some(updates.get.last.updateId + 1)
      fetchUpdateBehavior(bot, workerTimeout, updateId)
    }
}
