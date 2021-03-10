package PPS19.scalagram.examples.dsl

import PPS19.scalagram.dsl._
import PPS19.scalagram.models.MessageUpdate
import PPS19.scalagram.models.messages.{ChatMemberRemoved, ChatMembersAdded}
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
    >>
      """Hi! I will respond to some commands. Type
    |/hello to get some user's info,
    |/random to get a random number from 0 to 1000,
    |/hug to get a virtual hug from me.
    |I will also react when someone enter or leave this chat!
    |""".stripMargin

    << "/hello"
    >> { context =>
      context.reply(
        s"""Hi again! I bet your first name is
        |${context.from.get.firstName}
        |${if (context.from.get.lastName.isEmpty) "" else s"Your last name is ${context.from.get.lastName.get}"}
        |Your username is @${context.from.get.username.get}
        |""".stripMargin
      )
    }

    << "/random"
    >> { context =>
      context.reply(
        "The random number is " + Random.nextInt(1000) + "!"
      )
    }

    << "/hug"
    >> "I'm hugging you! (No worries, I'm COVID-free)"

    </ *
    >> { context =>
      context.reply(
        s"""${context.update.get
          .asInstanceOf[MessageUpdate]
          .message
          .asInstanceOf[ChatMemberRemoved]
          .leftChatMember
          .username
          .get} has left the chat. Goodbye!""".stripMargin
      )
    }

    <+ *
    >> { context =>
      context.reply(
        s"""${context.update.get
          .asInstanceOf[MessageUpdate]
          .message
          .asInstanceOf[ChatMembersAdded]
          .newChatMembers
          .head
          .username
          .get} has entered the chat. Welcome!""".stripMargin
      )
    }
  )
}

object CommandsBotMain extends App {
  CommandsBot.start()
  println("Commands bot started")
}
