package PPS19.scalagram.logic.scenes

/** A scoped set of actions that the bot can perform under certain circumstances.
  * A scene can be activated by name with the [[PPS19.scalagram.logic.Context.enterScene]] method.
  * When a scene is activated, the first scene step is automatically activated as well.
  * To leave a scene, the [[PPS19.scalagram.logic.Context.leaveScene]] method must be called.
  * The active scene step has a lower priority compared to the bot's reactions.
  *
  * @param name  Name of the scene.
  * @param steps Steps of the scene.
  */
case class Scene(name: String, steps: List[Step])
