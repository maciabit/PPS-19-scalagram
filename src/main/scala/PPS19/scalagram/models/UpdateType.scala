package PPS19.scalagram.models

/** Enumeration representing the type of an update, can be either "MessageReceived", "ChannelPostReceived",
  * "MessageEdited", "ChannelPostEdited" "CallbackSelected", "MessagePinned", "ChatMembersAdded", "ChatMemberRemoved"
  * or "Unknown",
  */
object UpdateType extends Enumeration {
  type UpdateType = Value
  val MessageReceived, ChannelPostReceived, MessageEdited, ChannelPostEdited, CallbackSelected, MessagePinned,
      ChatMembersAdded, ChatMemberRemoved, Unknown = Value
}
