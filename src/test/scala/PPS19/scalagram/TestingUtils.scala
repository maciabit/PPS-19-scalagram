package PPS19.scalagram

import java.nio.file.{Files, Paths}

object TestingUtils {
  def getJsonString(s: String): String = {
    new String(Files.readAllBytes(Paths.get(getClass.getClassLoader.getResource(s).toURI)))
  }
}
