package PPS19.scalagram.dsl.reactions

import PPS19.scalagram.dsl.scenes.steps.PartialStepContainer
import PPS19.scalagram.models.{InlineKeyboardMarkup, ReplyKeyboardMarkup}

object ReactionUtils {
  def HTML(message: String): MessageContainer =
    MessageContainer(message, Some("HTML"), None)

  def Markdown(message: String): MessageContainer =
    MessageContainer(message, Some("Markdown"), None)

  def MarkdownV2(message: String): MessageContainer =
    MessageContainer(message, Some("MarkdownV2"), None)

  case class MessageContainer(
      message: String,
      parseMode: Option[String],
      keyboard: Option[Either[ReplyKeyboardMarkup, InlineKeyboardMarkup]]
  ) {

    def -(replyKeyboard: ReplyKeyboardMarkup): MessageContainer =
      MessageContainer(message, parseMode, Some(Left(replyKeyboard)))

    def -(inlineKeyboard: InlineKeyboardMarkup): MessageContainer =
      MessageContainer(message, parseMode, Some(Right(inlineKeyboard)))

  }

  implicit class StringExtension(string: String) {

    def |(string2: String) = List(string2, string)

    def <|(stepName: String): PartialStepContainer = PartialStepContainer(string, stepName, Nil)
  }

  implicit class ListExtension(list: List[String]) {
    def |(string: String): List[String] = string +: list
  }

}
