package PPS19.scalagram.models

import PPS19.scalagram.models.messages.TextMessage
import io.circe.parser.decode
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ModelsSuite extends AnyFunSuite{


  test("ChannelPostTest"){
    //val message = """{ "update_id": 2, "message":{"message_id" : 1, "chat":1, "date":2, "text":"Piccolo bat"} }"""
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
}
