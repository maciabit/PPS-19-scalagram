package PPS19.scalagram.examples.dsl

import PPS19.scalagram.dsl._
import PPS19.scalagram.models.payloads.TextMessage
import PPS19.scalagram.models.updates.MessageUpdate
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
    >> """Hi! I will ask you some questions and generate your bio!
      |Type /bio to start.
      |""".stripMargin

    << "/bio"
    >> { context =>
      context.reply("Bio scene has started! Tell me your first name")
      context.enterScene("BIO_SCENE")
    }
  )

  scenes(
    scene(
      "BIO_SCENE"

      <| "Name_Step"
      >> { context =>
        val name = context.payload match {
          case m: TextMessage => m.text
          case _ => ""
        }
        context.store += "name" -> name
        context.reply(s"Okay, $name. Now tell me your last name.")
        context.nextStep()
      }

      <| "LastName_Step"
      >> { context =>
        val lastName = context.payload match {
          case m: TextMessage => m.text
          case _ => ""
        }
        context.store += "lastName" -> lastName
        context.reply("Got it. Last question: when were you born?")
        context.nextStep()
      }

      <| "Birthday_Step"
      >> { context =>
        val birthDate = context.payload match {
          case m: TextMessage => m.text
          case _ => ""
        }
        val identityCard =
          s"""Name: ${context.store("name")}
             |Surname: ${context.store("lastName")}
             |Birth date: $birthDate""".stripMargin
        context.reply("Here is your ID")
        context.reply(identityCard)
        context.leaveScene()
      }
    )
  )
}

object ScenesBotMain extends App {
  ScenesBot.start()
  println("Commands bot started")
}
