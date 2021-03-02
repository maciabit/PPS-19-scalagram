package PPS19.scalagram.models

import java.nio.file.{Files, Paths}

object Utils {
  def getJsonString(s: String): String = {
    new String(Files.readAllBytes(Paths.get(getClass.getClassLoader.getResource(s).toURI)))
  }
}
