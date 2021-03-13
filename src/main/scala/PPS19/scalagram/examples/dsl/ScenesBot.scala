package PPS19.scalagram.examples.dsl

import PPS19.scalagram.dsl._
import PPS19.scalagram.models.MessageUpdate
import PPS19.scalagram.models.messages.TextMessage
import PPS19.scalagram.utils.Props

object ScenesBot extends TelegramBotDSL {

  token(
    Props.get("token")
  )

  mode(
    Polling
  )

  reactions(
    !!
      >>
        """Hi! I will ask you some questions and generate your bio! Type
    |/bio to start.
    |""".stripMargin

      << "/bio"
      >> { context =>
        context.reply("Bio scene has started! Tell me your first name")
        context.enterScene("BIO_SCENE")
      }
  )

  scenes(
    scene(
      "Bio_Scene"

        <| "Name_Step"
        >> { context =>
          context.reply(s"""Okay ${context.update
            .asInstanceOf[MessageUpdate]
            .message
            .asInstanceOf[TextMessage]
            .text}! Now tell me your last name""")
          context.store += "Name" -> context.update.asInstanceOf[MessageUpdate].message.asInstanceOf[TextMessage].text
          context.nextStep()
        }

        <| "LastName_Step"
        >> { context =>
        context.reply("Got it. When were you born?")
        context.store += "Lastname" -> context.update.asInstanceOf[MessageUpdate].message.asInstanceOf[TextMessage].text
        context.nextStep()
      }

        <| "Birthday_Step"
        >> { context =>
        context.reply("Last question. When were you born?")
        context.store += "Birthday" -> context.update.asInstanceOf[MessageUpdate].message.asInstanceOf[TextMessage].text
        context.leaveScene()
      }
    )
  )
}

object ScenesBotMain extends App {
  ScenesBot.start()
  println("Commands bot started")
}
