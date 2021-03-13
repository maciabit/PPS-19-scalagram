package PPS19.scalagram.models.updates

import PPS19.scalagram.models.Chat

/** Represents an update belonging to a chat. Used to retrieve the update's chat. */
trait ChatUpdate extends Update {

  /** The chat the update belongs to. */
  def chat: Chat
}
