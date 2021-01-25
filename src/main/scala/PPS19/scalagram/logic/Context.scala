package PPS19.scalagram.logic

sealed trait Context {
  var activeScene: Option[Scene]
}

object Context {
  def apply(): Context = ContextImpl()
  case class ContextImpl() extends Context {
    override var activeScene: Option[Scene] = None
  }
}