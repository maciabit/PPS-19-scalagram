package PPS19.scalagram.models

import io.circe.parser.decode
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ModelsSuite extends AnyFunSuite {

  test("A Channel Post can be received and decoded") {
    val message = """{
                                "update_id": 971212771,
                                "channel_post": {
                                    "message_id": 344544,
                                    "author_signature": "ProphecyOfferte",
                                    "chat": {
                                        "id": -1001364807173,
                                        "title": "Bot Log",
                                        "type": "channel"
                                    },
                                    "date": 1611486001,
                                    "text": "Alive at 2021-01-24 12:00:00"
                                }
                            }"""
    val messageDec = decode[Update](message)
    assert(messageDec.isRight)
  }
  test("A Pinned Message alert can be received and decoded") {
    val message = """{
            "update_id": 844881872,
            "message": {
                "message_id": 515,
                "from": {
                    "id": 1543598092,
                    "is_bot": true,
                    "first_name": "ScalagramTest",
                    "username": "ScalagramTestBot"
                },
                "chat": {
                    "id": -1001286594106,
                    "title": "NodeBots",
                    "type": "supergroup"
                },
                "date": 1611909473,
                "pinned_message": {
                    "message_id": 510,
                    "from": {
                        "id": 263890809,
                        "is_bot": false,
                        "first_name": "Francesco",
                        "last_name": "Boschi",
                        "username": "Flavietto4"
                    },
                    "chat": {
                        "id": -1001286594106,
                        "title": "NodeBots",
                        "type": "supergroup"
                    },
                    "date": 1611909398,
                    "text": "Leggi"
                }
            }
        }"""
    val messageDec = decode[Update](message)
    assert(messageDec.isRight)
  }

  test("A New Members Added Message can be received and decoded") {
    val message =
      """{
            "update_id": 786603890,
            "message": {
                "message_id": 11429,
                "from": {
                    "id": 235157159,
                    "is_bot": false,
                    "first_name": "Macia",
                    "username": "mac1ia"
                },
                "chat": {
                    "id": -1001286594106,
                    "title": "Scala&NodeBots",
                    "type": "supergroup"
                },
                "date": 1613118320,
                "new_chat_participant": {
                    "id": 1566315552,
                    "is_bot": true,
                    "first_name": "ScalagramTestWindows_11",
                    "username": "ScalagramTestWindows_11Bot"
                },
                "new_chat_member": {
                    "id": 1566315552,
                    "is_bot": true,
                    "first_name": "ScalagramTestWindows_11",
                    "username": "ScalagramTestWindows_11Bot"
                },
                "new_chat_members": [
                    {
                        "id": 1566315552,
                        "is_bot": true,
                        "first_name": "ScalagramTestWindows_11",
                        "username": "ScalagramTestWindows_11Bot"
                    },
                    {
                        "id": 1576184053,
                        "is_bot": true,
                        "first_name": "ScalagramTestWindows_14",
                        "username": "ScalagramTestWindows_14Bot"
                    },
                    {
                        "id": 1697777709,
                        "is_bot": true,
                        "first_name": "ScalagramTestWindows_8",
                        "username": "ScalagramTestWindows_8Bot"
                    }
                ]
            }
        }"""
    val messageDec = decode[Update](message)
    assert(messageDec.isRight)
  }

  test("A Chat Member Removed Message can be received and decoded") {
    val message =
      """{
            "update_id": 786603889,
            "message": {
                "message_id": 11428,
                "from": {
                    "id": 235157159,
                    "is_bot": false,
                    "first_name": "Macia",
                    "username": "mac1ia"
                },
                "chat": {
                    "id": -1001286594106,
                    "title": "Scala&NodeBots",
                    "type": "supergroup"
                },
                "date": 1613118284,
                "left_chat_participant": {
                    "id": 1566315552,
                    "is_bot": true,
                    "first_name": "ScalagramTestWindows_11",
                    "username": "ScalagramTestWindows_11Bot"
                },
                "left_chat_member": {
                    "id": 1566315552,
                    "is_bot": true,
                    "first_name": "ScalagramTestWindows_11",
                    "username": "ScalagramTestWindows_11Bot"
                }
            }
        }
      """
    val messageDec = decode[Update](message)
    assert(messageDec.isRight)
  }
}
