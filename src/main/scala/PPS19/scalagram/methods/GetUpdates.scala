package PPS19.scalagram.methods

import PPS19.scalagram.models.BotToken
import PPS19.scalagram.models.updates.Update
import io.circe.Json
import io.circe.parser._
import requests.Requester

import scala.util.{Success, Try}

/** Use this method to receive incoming updates using long polling (wiki). A List of Update objects is returned.
  *
  * @param token          Token of the bot that will perform the requets.
  * @param offset         Identifier of the first update to be returned.
  *                       Must be greater by one than the highest among the identifiers of previously received updates.
  *                       By default, updates starting with the earliest unconfirmed update are returned.
  *                       An update is considered confirmed as soon as getUpdates is called with an offset higher than its updateId.
  *                       The negative offset can be specified to retrieve updates starting from -offset update from the end of the updates queue.
  *                       All previous updates will forgotten.
  * @param limit          Limits the number of updates to be retrieved. Values between 1-100 are accepted.
  *                       Defaults to 100.
  * @param timeout        Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling.
  * @param allowedUpdates List the types of updates you want your bot to receive.
  *                       Specify an empty list to receive all updates regardless of type (default).
  *                       If not specified, the previous setting will be used.
  *                       Please note that this parameter doesn't affect updates created before the call to the getUpdates,
  *                       so unwanted updates may be received for a short period of time.
  */
case class GetUpdates(
    token: BotToken,
    offset: Option[Long] = None,
    limit: Option[Int] = None,
    timeout: Option[Int] = None,
    allowedUpdates: Option[Array[String]] = None
) extends TelegramRequest[List[Update]] {

  val request: Requester = requests.get

  val endpoint: String = "getUpdates"

  val urlParams: Map[String, Any] = Map(
    "offset" -> offset,
    "limit" -> limit,
    "timeout" -> timeout,
    "allowed_Updates" -> allowedUpdates
  )

  def parseSuccessfulResponse(json: Json): Try[List[Update]] = {
    val updates = json.asArray
      .getOrElse(Vector.empty)
      .map { update => decode[Update](update.toString()) }
      .collect { case Right(update) => update }
      .toList
    Success(updates)
  }
}
