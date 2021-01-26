package PPS19.scalagram.methods

import PPS19.scalagram.utils.Props

class SendMessage(no_webpage: Option[Boolean] = None,
                  silent: Option[Boolean] = None,
                  background: Option[Boolean] = None,
                  clear_draft: Option[Boolean] = None,
                  peer: Peer,
                  reply_to_msg_id: Option[Int] = None,
                  message: String,
                  //random_id: Long,
                  reply_markup: Option[Markup] = None,
                  entities: Option[Vector[Any]] = None,
                  schedule_date: Option[Int] = None
                 ){

  val token = ""

  def sendMessage(): Int = {
    val req = requests.post("https://api.telegram.org/bot"+Props.get("token")+"/sendMessage?chat_id="+peer.chatId+"&text="+message)
    req.statusCode
  }
}
