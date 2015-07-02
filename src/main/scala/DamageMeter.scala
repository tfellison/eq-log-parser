/**
 * Created by Taylor Ellison on 6/28/2015.
 */
import scala.swing._
import scala.sys.process._
import scala.util.matching.Regex
import java.time


class DamageMeter(windowPeriods: String) {

  private val RollingWindowDamageDescription = " seconds: "
  private val CurrentDamageDescription = "Current Fight"
  private val currentDamageLabel = new Label{text = CurrentDamageDescription; foreground = java.awt.Color.WHITE } // This label will be added to primary frame; value of text property will be updated when new data is available

  private var currentDamageOutput = 0.0

  private val windowPeriodsArray = windowPeriods.split(",")
  private var windowPeriodsMap:Map[Int,Label] = Map()
  for (i <- 0 to windowPeriodsArray.length - 1 ) {
    windowPeriodsMap += (windowPeriodsArray(i).toInt -> new Label{text = windowPeriodsArray(i) + RollingWindowDamageDescription + 0.0; foreground = java.awt.Color.WHITE })
  }

  // This MainFrame is the primary frame representing the DamageMeter itself
  private val mainFrame = new MainFrame {
    title = "EQLogParser - DamageMeter"
    contents = new GridPanel(1,2) {
      contents += currentDamageLabel
      background = new Color(40,0,0)
      contents += new GridPanel(windowPeriodsMap.size,1) {
        for (period <- windowPeriodsMap.values) {
          contents += period
        }
        background = new Color(40,0,0)
      }
      iconImage = toolkit.getImage("src\\main\\resources\\eq_logo.png")
    }
    size = new Dimension(500,300)
    centerOnScreen()
  }

  def processNewLine(line: String): Unit = {
    println(currentDamageOutput)
  }

  /**
   * Re-calculate all damage values and update label text values with new information
   */
  private def updateOutputValues(): Unit = {
    currentDamageLabel.text = "<html><body style='text-align:center'>" + CurrentDamageDescription + "<br/><br/><div style='font-size:56px'>" + currentDamageOutput + "</div></body></html>"
    //fiveSecondDamageLabel.text = "<html><body>" + FiveSecondDamageDescription + "<span style='font-size:14px'>" + fiveSecondDamageOutput + "</span></body></html>"
  }

  /**
   * Display the DamageMeter and begin it's execution
   */
  def launch(): Unit = {
    updateOutputValues()
    mainFrame.visible = true
  }
}
