package PPS19.scalagram.dsl.mode

import PPS19.scalagram.modes.polling.Polling.{defaultPollingInterval, defaultTimeoutDelay}

import scala.concurrent.duration.FiniteDuration

/** Container used to build a bot's working mode.
  *
  * Used by [[PollingModeContainer]].
  * Will also be used by WebhookModeContainer once that is implemented.
  */
sealed trait WorkingModeContainer

/** Container used to build a bot's [[PPS19.scalagram.modes.polling.Polling]].
  *
  * @param pollingInterval Interval at which updates are fetched.
  * @param timeoutDelay Timeout after which, if no updates are received, the bot's context gets destroyed.
  */
case class PollingModeContainer(
    pollingInterval: FiniteDuration = defaultPollingInterval,
    timeoutDelay: FiniteDuration = defaultTimeoutDelay
) extends WorkingModeContainer {

  /** Creates a copy of this [[PollingModeContainer]] with [[pollingInterval]] set as a parameter.
    *
    * @param pollingInterval Interval at which updates are fetched.
    */
  def interval(pollingInterval: FiniteDuration): PollingModeContainer =
    PollingModeContainer(pollingInterval, this.timeoutDelay)

  /** Creates a copy of this [[PollingModeContainer]] with [[timeoutDelay]] set as a parameter.
    *
    * @param timeoutDelay Timeout after which, if no updates are received, the bot's context gets destroyed.
    */
  def timeoutDelay(timeoutDelay: FiniteDuration): PollingModeContainer =
    PollingModeContainer(this.pollingInterval, timeoutDelay)
}
