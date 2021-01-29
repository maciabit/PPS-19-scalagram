package PPS19.scalagram.models

sealed trait Markup {
  def selective: Boolean
}

object Markup

case class KeyboardHide (selective: Boolean)
case class KeyboardForceReply (selective: Boolean, single_use: Boolean)
case class KeyboardMarkup (selective: Boolean, single_use: Boolean, resize: Boolean, rows: Vector[String])
//case class InlineMarkup
