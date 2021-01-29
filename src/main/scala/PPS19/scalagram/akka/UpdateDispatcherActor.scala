package PPS19.scalagram.akka

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
  private def getUpdate: Update = updates(scala.util.Random.nextInt(updates.length))

  def apply(interval: FiniteDuration, workerTimeout: FiniteDuration): Behavior[LookForUpdates] =
    scheduleUpdateBehavior(interval, workerTimeout)

  private def scheduleUpdateBehavior(interval: FiniteDuration, workerTimeout: FiniteDuration): Behavior[LookForUpdates] =
    Behaviors.withTimers { timers =>
      timers.startTimerAtFixedRate(LookForUpdates(), interval)
      fetchUpdateBehavior(workerTimeout)
    }

  private def fetchUpdateBehavior(workerTimeout: FiniteDuration): Behavior[LookForUpdates] =
    Behaviors.receive { (context, _) =>
      context.log.info("Fetching updates")
      val update = getUpdate
      val workerName = s"worker${update.chatId}"
      val worker = context.child(workerName) match {
        case Some(actor: ActorRef[ProcessUpdate]) => actor
        case None => context.spawn(WorkerActor(workerTimeout), workerName)
      }
      worker ! ProcessUpdate(update)
      Behaviors.same
    }
}
