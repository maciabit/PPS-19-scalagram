package PPS19.scalagram.utils

import java.net.URL
import java.util.Properties
import scala.io.Source

object Props {

  val FILE_NAME = "application.properties"

  private val properties: Properties = new Properties()

  def get(property: String): String = {
    if (properties.isEmpty) load()
    properties.getProperty(property)
  }

  def load(): Unit = {
    if (!properties.isEmpty) return
    val url: URL = getClass.getClassLoader.getResource(FILE_NAME)
    if (url == null) return
    val source = Source.fromURL(url)
    properties.load(source.bufferedReader())
  }
}
