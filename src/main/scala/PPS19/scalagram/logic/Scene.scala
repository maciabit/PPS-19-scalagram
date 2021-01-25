package PPS19.scalagram.logic

sealed trait Scene {
  val reactions: List[Reaction]
}

object Scene {

  def apply(reactions: Reaction*): SceneImpl = SceneImpl(reactions.toList)

  def unapply(scene: Scene, index: Int): Reaction = scene.reactions(index)

  case class SceneImpl(reactions: List[Reaction]) extends Scene
}
