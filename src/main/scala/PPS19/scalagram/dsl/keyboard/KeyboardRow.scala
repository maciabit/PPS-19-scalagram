package PPS19.scalagram.dsl.keyboard

/** A row of buttons inside of a Reply Keyboard or Inline Keyboard.
  *
  * @param buttons Buttons inside the row
  */
case class KeyboardRow(buttons: Seq[KeyboardButtonContainer]) {

  /** Creates a copy of this [[KeyboardRow]] with an additional button appended at the end.
    *
    * @param button Button to append. Can accept a string and perform an implicit conversion.
    */
  def ::(button: KeyboardButtonContainer): KeyboardRow = KeyboardRow(button +: buttons)
}
