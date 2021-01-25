package PPS19.scalagram.methods

sealed trait Peer {
  def chatId: String
}

final case class InputPeerChat (chatId: String) extends Peer
