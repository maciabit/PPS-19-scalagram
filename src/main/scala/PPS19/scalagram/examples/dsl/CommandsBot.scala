package PPS19.scalagram.examples.dsl

import PPS19.scalagram.dsl._
import PPS19.scalagram.models.payloads.{ChatMemberRemoved, ChatMembersAdded}
import PPS19.scalagram.models.updates.MessageUpdate
import PPS19.scalagram.utils.Props

import scala.util.Random

object CommandsBot extends TelegramBotDSL {

  token(
    Props.get("token")
  )

  mode(
    Polling
  )

  reactions(
    !!
    >> """Hi! I will respond to some commands. Type:
      |/hello to get a greeting
      |/random to get a random number
      |/hug to get a virtual hug from me
      |I will also react when someone enters or leaves this chat!
      |""".stripMargin

    << "/hello"
    >> { context =>
      context.reply(
        s"""Hi again! I bet you are ${context.from.get.firstName}.
        |${if (context.from.get.lastName.isEmpty) "" else s"Your last name is ${context.from.get.lastName.get}"}
        |Your username is @${context.from.get.username.get}.
        |""".stripMargin
      )
    }

    << "/random"
    >> { context => context.reply("The random number is " + Random.nextInt(1000) + "!") }

    << "/hug"
    >> "I'm hugging you! (No worries, I'm COVID-free)"

    <+ *
    >> { context =>
      context.payload match {
        case ChatMembersAdded(_, _, _, u) =>
          context.reply(s"${u.head.username.get} has entered the chat. Welcome!")
        case _ =>
      }
    }

    </ *
    >> { context =>
      context.payload match {
        case ChatMemberRemoved(_, _, _, u) => context.reply(s"${u.username.get} has left the chat. Goodbye!")
        case _ =>
      }
    }
  )
}

object CommandsBotMain extends App {
  CommandsBot.start()
  println("Commands bot started")
}
