package PPS19.scalagram.logic

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ContextSuite extends AnyFunSuite with BeforeAndAfterEach {

  val scene: Scene = Scene(
    "TEST_SCENE",
    List(
      Step("STEP1", _ => {}),
      Step("STEP2", _ => {}),
      Step("STEP3", _ => {})
    )
  )
  val bot: Bot = Bot(BotToken(""), List(), List(), List(scene))
  var context: Context = _

  override def beforeEach(): Unit = {
    context = Context(bot)
  }

  test("The enterScene method works as intended") {
    context.enterScene("TEST_SCENE")
    assert(context.activeScene.contains(scene) && context.sceneStep.contains(scene.steps.head))
  }

  test("The nextStep method works as intended") {
    context.enterScene("TEST_SCENE")
    context.nextStep()
    assert(context.sceneStep.contains(scene.steps(1)))
    context.nextStep()
    assert(context.sceneStep.contains(scene.steps(2)))
    context.nextStep()
    assert(context.sceneStep.isEmpty)
  }

  test("The goToStep method works as intended") {
    context.enterScene("TEST_SCENE")
    context.goToStep(1)
    assert(context.sceneStep.contains(scene.steps(1)))
    context.goToStep("STEP1")
    assert(context.sceneStep.contains(scene.steps.head))
    context.goToStep("UNEXISTING_STEP")
    assert(context.sceneStep.isEmpty)
    context.leaveScene()
    context.goToStep(1)
    assert(context.sceneStep.isEmpty)
  }

  test("The leaveScene method works as intended") {
    context.enterScene("TEST_SCENE")
    context.leaveScene()
    assert(context.activeScene.isEmpty && context.sceneStep.isEmpty)
  }
}
