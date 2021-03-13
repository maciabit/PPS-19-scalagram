package PPS19.scalagram.dsl.scene

import PPS19.scalagram.logic.scenes.Scene

/** Container used to build a list of [[Scene]].
  *
  * @param scenes List of [[Scene]] to put inside of this container
  */
case class SceneContainer(scenes: List[Scene]) {

  /** Creates a new scene container with all the scenes from this one, plus a new one created from the [[TotalStepContainer]] given as a parameter.
    *
    * @param stepContainer Container used for building the new scene
    */
  def scene(stepContainer: TotalStepContainer): SceneContainer =
    SceneContainer(scenes :+ Scene(stepContainer.sceneName, stepContainer.steps))
}
