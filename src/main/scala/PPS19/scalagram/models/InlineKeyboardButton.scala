package PPS19.scalagram.models

/*MUST use exactly one of the optional fields!*/
case class InlineKeyboardButton(text:String, url:Option[String] = None, login_url : Option[LoginUrl] = None, callback_data : Option[String] = None, switch_inline_query : Option[String] = None, switch_inline_query_current_chat : Option[String] = None, pay : Option[Boolean] = None)
