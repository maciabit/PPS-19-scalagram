package PPS19.scalagram.dsl.middleware

import PPS19.scalagram.logic.{Context, Middleware}

trait MiddlewareContainer {
  def middlewares: List[Middleware]
}

case class MiddlewareContainerConcatenation(middlewares: List[Middleware])
    extends MiddlewareContainer {
  def <->(middleware: Context => Boolean): MiddlewareContainerConcatenation =
    MiddlewareContainerConcatenation(
      middlewares appended Middleware(middleware)
    )
}
