package PPS19.scalagram.modes

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import scala.concurrent.duration._

case class Update(chatId: Int, message: String)

object WorkerActor {

  final case class ProcessUpdate(update: Update)

  def apply(): Behavior[ProcessUpdate] = Behaviors.receive { (context, message) =>
    context.log.info("Update {}", message.update)
    Behaviors.same
  }
}

object UpdateDispatcherActor {

  // Fake updates
  private val updates = List(
    Update(100, "Message from Gianni"),
    Update(200, "Message from Met"),
    Update(300, "Message from Flavio"),
  )
  private def getUpdate: Update = updates(scala.util.Random.nextInt(3))

  final case class LookForUpdates()

  def apply(): Behavior[LookForUpdates] = scheduleUpdateBehavior()

  private def scheduleUpdateBehavior(): Behavior[LookForUpdates] = Behaviors.withTimers { timers =>
    timers.startTimerAtFixedRate(UpdateDispatcherActor.LookForUpdates(), 5.seconds)
    fetchUpdateBehavior()
  }

  private def fetchUpdateBehavior(): Behavior[LookForUpdates] = Behaviors.receive { (context, _) =>
    context.log.info("Looking for updates")

    val update = getUpdate
    val workerName = s"worker${update.chatId}"

    val worker = context.child(workerName) match {
      case Some(actor: ActorRef[WorkerActor.ProcessUpdate]) => actor
      case None => context.spawn(WorkerActor(), workerName)
    }
    worker ! WorkerActor.ProcessUpdate(update)
    Behaviors.same
  }
}

object Polling extends App {

  // Create the actor system with an UpdateDispatcherActor as guardian
  val system: ActorSystem[UpdateDispatcherActor.LookForUpdates] = ActorSystem(UpdateDispatcherActor(), "dispatcher")
  val updateDispatcher: ActorRef[UpdateDispatcherActor.LookForUpdates] = system

  // Send a LookForUpdates message to UpdateDispatcherActor to trigger update polling
  updateDispatcher ! UpdateDispatcherActor.LookForUpdates()
}