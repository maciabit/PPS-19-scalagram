# ScalaGram

![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/maciabit/PPS-19-scalagram/Scala%20CI%20with%20Gradle/develop)
![GitHub contributors](https://img.shields.io/github/contributors/maciabit/PPS-19-scalagram)
![GitHub last commit (branch)](https://img.shields.io/github/last-commit/maciabit/PPS-19-scalagram/develop)
![GitHub](https://img.shields.io/github/license/maciabit/PPS-19-scalagram)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/maciabit/PPS-19-scalagram)

## Overview

ScalaGram is a library for building Telegram bots easily, thanks to the help of functional programming and a powerful DSL.

## Report

This library was born as project for the courses **Programming and Development Paradigms** and **Laboratory of Software Systems** at the University of Bologna, campus of Cesena (IT).\
[Check out the final report for the project](https://github.com/maciabit/PPS-19-scalagram/blob/main/docs/report.md) (written in italian).

## Basic example
``` scala
import PPS19.scalagram.dsl._

object ScalagramBot extends ScalagramDSL {

  token("<YOUR_BOT_TOKEN>")

  mode(Polling)

  reactions(
    // Response to the "/start" command (mandatory)
    !!
    >> "Hello, World!"

    // Response to the "/hello" command
    << "/hello"
    >> { context => context.reply(s"Hi, ${context.from.get.firstName}") }
  )
}

object CommandsBotMain extends App {
  CommandsBot.start()
}
```

## Getting started

Gradle dependency:
```
coming soon
```

sbt dependency:
```
coming soon
```

Build from source:
```
git clone https://github.com/maciabit/PPS-19-scalagram
cd PPS-19-scalagram
./gradlew build
```

## Test
```
./gradlew test
```

## Implemented features

Telegram API methods:
- AnswerCallbackQuery
- DeleteMessage
- EditMessage
- GetMe
- GetUpdates
- PinMessage
- SendMessage
- SendPhoto
- UnpinAllMessages
- UnpinMessage

Updates handled by the library:
- CallbackButtonSelected
- ChannelPostEdited
- ChannelPostReceived
- MessageEdited
- MessageReceived

Messages handled by the library:
- CallbackQuery
- ChatMemberRemoved
- ChatMembersAdded
- MessagePinned
- PhotoMessage
- TextMessage

Working modes:
- Polling

## Contribution
If you want to contribute to the project, Pull Requests are welcome.

## Authors
- [Francesco Boschi](https://github.com/FrancescoBoschi)
- [Filippo Pistocchi](https://github.com/pistocchifilippo)
- [Mattia Rossi](https://github.com/maciabit)
- [Gianni Tumedei](https://github.com/gianni-tumedei-studio-unibo)