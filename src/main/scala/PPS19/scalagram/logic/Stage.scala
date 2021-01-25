package PPS19.scalagram.logic

sealed trait Stage {
  val scenes: List[Scene]
  def setActiveScene(scene: Scene): Unit
}

object Stage {

  def apply(scenes: Scene*): StageImpl = StageImpl(scenes.toList)

  def unapply(stage: Stage, index: Int): Scene = stage.scenes(index)

  case class StageImpl(scenes: List[Scene]) extends Stage {
    var activeScene: Option[Scene] = None

    override def setActiveScene(scene: Scene): Unit = {
      if (!scenes.contains(scene)) {
        throw new IllegalArgumentException("The stage does not contain the provided scene")
      }
      activeScene = Some(scene)
    }
  }
}
