package PPS19.scalagram.utils

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

import java.net.URL

@RunWith(classOf[JUnitRunner])
class PropsSuite extends AnyFunSuite {

  test("An application.properties file exists in project resources") {
    val url: URL = getClass.getClassLoader.getResource(Props.FILE_NAME)
    assert(url != null)
  }

  test("The bot token needed for testing can be read from application.properties") {
    Props.load()
    val token = Props.get("token")
    assert(token != null)
  }
}
