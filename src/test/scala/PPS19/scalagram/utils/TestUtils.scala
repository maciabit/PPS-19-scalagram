package PPS19.scalagram.utils

import scala.util.Try

object TestUtils{
  @annotation.tailrec
  def retry[T](fn: => T)(n: Int): util.Try[T] = {
    util.Try { fn } match {
      case util.Failure(_) if n > 1 => retry(fn)(n - 1)
      case fn => fn
    }
  }
}
