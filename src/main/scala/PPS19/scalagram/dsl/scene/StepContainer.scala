package PPS19.scalagram.dsl.scene

import PPS19.scalagram.logic.Context
import PPS19.scalagram.logic.scenes.Step

/** Container used to build a single [[PPS19.scalagram.logic.scenes.Scene]]
  *
  * Used by [[PartialStepContainer]] and [[TotalStepContainer]]
  */
trait StepContainer {

  /** Name of the scene */
  def sceneName: String

  /** Steps of the scene */
  def steps: List[Step]
}

/** Container for a partially defined [[PPS19.scalagram.logic.scenes.Scene]], that includes a name for the scene, a list of steps and a name for the new step to be added.
  * A [[PartialStepContainer]] cannot be passed to a bot's [[PPS19.scalagram.dsl.ScalagramDSL.scene]] method,
  * because it includes a step that is not totally defined.
  * A partially defined scene can be terminated by concatenating an action using the [[>>]] method.
  *
  * @param sceneName Name of the scene
  * @param stepName  Name of the new step to be added
  * @param steps     Steps of the scene
  *
  *                  Extends [[StepContainer]].
  */
case class PartialStepContainer(sceneName: String, stepName: String, steps: List[Step]) extends StepContainer {

  /** Creates a [[TotalStepContainer]] with the same name of this one and the same steps, plus a new one appended at the end.
    *
    * @param action Function to be performed by the new step.
    */
  def >>(action: Context => Unit): TotalStepContainer =
    TotalStepContainer(sceneName, steps :+ Step(stepName, action))
}

/** Container that has list of reactions.
  * A [[TotalStepContainer]] can be passed to a bot's [[PPS19.scalagram.dsl.ScalagramDSL.scene]] method.
  * To concatenate another step, use the [[<|]] method.
  *
  * @param sceneName Name of the scene
  * @param steps     Steps of the scene
  *
  *                  Extends [[StepContainer]].
  */
case class TotalStepContainer(sceneName: String, steps: List[Step]) extends StepContainer {

  /** Creates a [[PartialStepContainer]] with the same name of this one and the same steps, plus a name for a new step to be added.
    *
    * @param stepName Name of the new step.
    */
  def <|(stepName: String): PartialStepContainer = PartialStepContainer(sceneName, stepName, steps)
}
