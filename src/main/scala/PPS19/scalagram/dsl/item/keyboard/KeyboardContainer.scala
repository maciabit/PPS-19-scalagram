package PPS19.scalagram.dsl.item.keyboard

trait KeyboardRow {

  def buttons: Seq[KeyboardButtonContainer]

  def ::(button: KeyboardButtonContainer): KeyboardRow
}

case class KeyboardRowImpl(buttons: Seq[KeyboardButtonContainer]) extends KeyboardRow {

  override def ::(button: KeyboardButtonContainer): KeyboardRow = KeyboardRowImpl(buttons :+ button)
}