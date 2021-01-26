package PPS19.scalagram.methods

import scala.collection.mutable

trait BotAction {

  def httpMethod: String
  def token: String
  def urlParams: mutable.Map[String, AnyVal]
}

object BotAction {

}
