package PPS19.scalagram.dsl.scenes.steps

import PPS19.scalagram.logic.{Context, Step}

trait StepContainer {
  def sceneName: String
  def steps: List[Step]
}

case class PartialStepContainer(sceneName: String, stepName: String, steps: List[Step]) extends StepContainer {
  def >>(action: Context => Unit): TotalStepContainer = TotalStepContainer(sceneName, steps :+ Step(stepName, action))
}

case class TotalStepContainer(sceneName: String, steps: List[Step]) extends StepContainer {
  def <|(stepName: String): PartialStepContainer = PartialStepContainer(sceneName, stepName, steps)
}
