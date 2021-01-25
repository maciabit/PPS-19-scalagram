package PPS19.scalagram.methods

sealed trait Peer {
  def chatId: String
}
object Peer
case class InputPeerChat (chatId: String) extends Peer
