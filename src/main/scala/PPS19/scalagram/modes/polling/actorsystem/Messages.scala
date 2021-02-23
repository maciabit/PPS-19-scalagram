package PPS19.scalagram.modes.polling.actorsystem

import PPS19.scalagram.models.Update

import java.time.LocalDateTime

// Fake update class
//case class Update(id: Int, chatId: Int, message: String)

/** Any message that a WorkerActor can handle */
sealed trait WorkerMessage

/** A message with an update to be processed */
final case class ProcessUpdate(update: Update) extends WorkerMessage

/** A message with the timestamp of an update */
final case class Timeout(timestamp: LocalDateTime) extends WorkerMessage

/** Any message that an UpdateDispatcherActor can handle */
sealed trait DispatcherMessage

/** A message telling the actor to look for new updates */
final case class LookForUpdates() extends DispatcherMessage
