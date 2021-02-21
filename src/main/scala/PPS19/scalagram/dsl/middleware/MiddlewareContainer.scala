package PPS19.scalagram.dsl.middleware

import PPS19.scalagram.logic.{Context, Middleware}

trait MiddlewareContainer {
  def middlewares: List[Middleware]
  def <->(middleware: Context => Boolean): MiddlewareContainer
}

object MiddlewareContainer {
  def apply(middlewares: List[Middleware]): MiddlewareContainer =
    MiddlewareContainerImpl(middlewares)

  private case class MiddlewareContainerImpl(middlewares: List[Middleware]) extends MiddlewareContainer {
    def <->(middleware: Context => Boolean): MiddlewareContainerImpl =
      MiddlewareContainerImpl(
        middlewares appended Middleware(middleware)
      )
  }
}
