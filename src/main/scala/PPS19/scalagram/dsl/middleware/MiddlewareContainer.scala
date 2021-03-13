package PPS19.scalagram.dsl.middleware

import PPS19.scalagram.logic.{Context, Middleware}

/** Container used to build a list of [[Middleware]].
  *
  * @param middlewares List of [[Middleware]] to put inside of this container
  */
case class MiddlewareContainer(middlewares: List[Middleware]) {

  /** Creates a copy of this [[MiddlewareContainer]] with an additional middleware appended at the end.
    *
    * @param middleware Operation that the new middleware will perform
    */
  def <>(middleware: Context => Boolean): MiddlewareContainer =
    MiddlewareContainer(middlewares :+ Middleware(middleware))
}
