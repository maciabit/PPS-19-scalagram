package PPS19.scalagram.utils

import java.net.URL
import java.util.Properties
import scala.io.Source

/** Object used as singleton to contain application's properties */
object Props {

  val FILE_NAME = "application.properties"

  private val properties: Properties = new Properties()

  /** Method used to retrieve a singleton property or create the singleton instance if is the first call.
    *
    * @param property The Property to retrieve.
    * @return The property.
    */
  def get(property: String): String = {
    if (properties.isEmpty) load()
    properties.getProperty(property)
  }

  /** Method used to create the singleton object and load the properties.. */
  def load(): Unit = {
    if (!properties.isEmpty) return
    val url: URL = getClass.getClassLoader.getResource(FILE_NAME)
    if (url == null) return
    val source = Source.fromURL(url)
    properties.load(source.bufferedReader())
  }
}
