package PPS19.scalagram.models

object UpdateType extends Enumeration {
  type UpdateType = Value
  val MessageReceived, ChannelPostReceived, MessageEdited, ChannelPostEdited, CallbackSelected, MessagePinned,
      ChatMembersAdded, ChatMemberRemoved, Unknown = Value
}
