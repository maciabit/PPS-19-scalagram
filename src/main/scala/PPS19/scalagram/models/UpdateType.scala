package PPS19.scalagram.models

import PPS19.scalagram.models.ChatType.Value

object UpdateType extends Enumeration {
  type UpdateType = Value
  val MessageReceived, MessageEdited, ChannelPost, ChannelPostEdited = Value
}
