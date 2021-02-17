package PPS19.scalagram.dsl.mode

import scala.concurrent.duration.FiniteDuration
import PPS19.scalagram.modes.polling.Polling.{
  defaultPollingInterval,
  defaultTimeoutDelay
}

object WorkingMode {

  def POLLING: Polling = Polling()

  def WEBHOOK: Webhook = Webhook()

  sealed trait WorkingMode

  case class Polling(
      pollingInterval: FiniteDuration = defaultPollingInterval,
      timeoutDelay: FiniteDuration = defaultTimeoutDelay
  ) extends WorkingMode {
    def interval(_pollingInterval: FiniteDuration): Polling =
      Polling(_pollingInterval, this.timeoutDelay)

    def timeoutDelay(_timeoutDelay: FiniteDuration): Polling =
      Polling(this.pollingInterval, _timeoutDelay)
  }

  case class Webhook() extends WorkingMode

}
