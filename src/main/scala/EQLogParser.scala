import java.io._
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.commons.io.input.Tailer
import org.apache.commons.io.input.TailerListener

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

    val logFile = new File(properties.getProperty("everquest-install-directory") + "Logs\\eqlog_" + properties.getProperty("character-name") + "_project1999.txt")

    // Create and launch a new DamageMeter object
    val damageMeter = new DamageMeter(windowPeriods = properties.getProperty("damage-meter-window-periods"), currentFightDelay = properties.getProperty("current-fight-delay").toInt)
    damageMeter.launch()

    // Create LogTailer and being executing it. The LogTailer will pass new lines to DamageMeter object for processing
    val listener = new LogTailer(damageMeter);
    val tailer = Tailer.create(logFile, listener, 1000);
  }
}