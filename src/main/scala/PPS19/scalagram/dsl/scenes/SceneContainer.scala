package PPS19.scalagram.dsl.scenes

import PPS19.scalagram.dsl.scenes.steps.TotalStepContainer
import PPS19.scalagram.logic.Scene

case class SceneContainer(scenes: List[Scene]) {
  def scene(stepContainer: TotalStepContainer): SceneContainer =
    SceneContainer(scenes :+ Scene(stepContainer.sceneName, stepContainer.steps))
}
