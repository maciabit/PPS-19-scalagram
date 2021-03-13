package PPS19.scalagram.examples.dsl

import PPS19.scalagram.dsl._
import PPS19.scalagram.utils.Props

object KeyboardsBot extends TelegramBotDSL {

  token(
    Props.get("token")
  )

  mode(
    Polling
  )

  reactions(
    !!
    >>
      """Hi! I can send various types of keyboards. Type:
    |/keyboard to get reply keyboard
    |/inlineKeyboard to get an inline keyboard
    |""".stripMargin

    << "/keyboard"
    >> "Here's a reply keyboard" - Keyboard(
      "Button 1",
      "Button 2" :: "Button 3"
    )

    << "/inlineKeyboard"
    >> "Here's an inline keyboard" - InlineKeyboard(
      Callback("Button 1" -> "callback"),
      Url("Click me!" -> "https://github.com/maciabit/PPS-19-scalagram")
    )

    <~ "callback"
    >> "You clicked button 1"
  )
}

object KeyboardsBotMain extends App {
  KeyboardsBot.start()
  println("Keyboards bot started")
}
