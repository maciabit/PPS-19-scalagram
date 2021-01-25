package PPS19.scalagram.models

final case class User(id: Int,
                      isBot: Boolean,
                      firstName: String,
                      lastName: Option[String],
                      username: Option[String],
                      languageCode: Option[String],
                      canJoinGroups: Option[Boolean],
                      canReadAllGroupMessages: Option[Boolean],
                      supportsInlineQueries: Option[Boolean])

