import java.io._
import java.util.Properties
import scala.collection.JavaConverters._

/**
 * Created by Taylor Ellison on 6/28/2015.
 */
object EQLogParser {

  def main(args: Array[String]): Unit = {

    // Read in configuration information from the properties file, and store in a Properties object
    val propertiesFile = new File("src\\main\\config\\EQLogParser.properties")
    val inputStream = new InputStreamReader(new FileInputStream(propertiesFile), "UTF-8")
    val properties = new Properties()
    properties.load(inputStream)

    // Create and launch a new DamageMeter object
    val damageMeter = new DamageMeter(properties.getProperty("damage-meter-window-periods"));
    damageMeter.launch()

  }
}