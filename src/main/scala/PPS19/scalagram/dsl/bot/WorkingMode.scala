package PPS19.scalagram.dsl.bot

import scala.concurrent.duration.FiniteDuration

object WorkingMode {

  def POLLING: Polling = Polling()

  def WEBHOOK: Webhook = Webhook()

  sealed trait WorkingMode

  case class Polling(
                      pollingInterval: FiniteDuration = PPS19.scalagram.modes.polling.Polling.defaultPollingInterval,
                      timeoutDelay: FiniteDuration = PPS19.scalagram.modes.polling.Polling.defaultTimeoutDelay,
                      debug: Boolean = false
                    ) extends WorkingMode {
    def interval(_pollingInterval: FiniteDuration): Polling = Polling(_pollingInterval, this.timeoutDelay, this.debug)

    def timeoutDelay(_timeoutDelay: FiniteDuration): Polling = Polling(this.pollingInterval, _timeoutDelay, this.debug)

    def debug(_debug: Boolean): Polling = Polling(this.pollingInterval, this.timeoutDelay, _debug)
  }

  case class Webhook() extends WorkingMode

}
