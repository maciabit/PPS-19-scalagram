package PPS19.scalagram.models.updates

import PPS19.scalagram.models.updates.UpdateType.UpdateType

/** Represent an unknown update. Useful to avoid unexpected crash.
  *
  * @param updateId Unique identifier for this update.
  *
  *                 Extends [[Update]]
  */
final case class UnknownUpdate(updateId: Long) extends Update {
  override def updateType: UpdateType = UpdateType.Unknown
}
