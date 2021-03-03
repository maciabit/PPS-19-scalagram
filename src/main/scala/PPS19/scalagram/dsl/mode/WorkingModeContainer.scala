package PPS19.scalagram.dsl.mode

import scala.concurrent.duration.FiniteDuration
import PPS19.scalagram.modes.polling.Polling.{defaultPollingInterval, defaultTimeoutDelay}

sealed trait WorkingModeContainer

case class PollingModeContainer(
    pollingInterval: FiniteDuration = defaultPollingInterval,
    timeoutDelay: FiniteDuration = defaultTimeoutDelay
) extends WorkingModeContainer {
  def interval(_pollingInterval: FiniteDuration): PollingModeContainer =
    PollingModeContainer(_pollingInterval, this.timeoutDelay)

  def timeoutDelay(_timeoutDelay: FiniteDuration): PollingModeContainer =
    PollingModeContainer(this.pollingInterval, _timeoutDelay)
}
