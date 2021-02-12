package PPS19.scalagram.dsl

import PPS19.scalagram.dsl.bot.WorkingMode._
import PPS19.scalagram.dsl.bot.ComposableBot

import scala.concurrent.duration.DurationInt

class DSL extends ComposableBot {

  token {
    "TOKEN"
  }

  mode {
    POLLING interval 300.milliseconds timeoutDelay 1.days debug false
  }

  reactions {
    in ("/HI") >> "HI" << "/HI" >> "HI" << "/HI" >> "HI"
  }

}
