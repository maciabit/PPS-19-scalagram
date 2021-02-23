package PPS19.scalagram.logic

sealed trait Scene {
  val name: String
  val steps: List[Step]
}

object Scene {

  def apply(name: String, steps: List[Step]): SceneImpl = SceneImpl(name, steps)

  def unapply(scene: Scene, index: Int): Step = scene.steps(index)

  case class SceneImpl(name: String, steps: List[Step]) extends Scene
}
