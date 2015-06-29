/**
 * Created by Taylor Ellison on 6/28/2015.
 */
import scala.swing._

class DamageMeter(windowPeriods: String) {

  // Constant declarations
  val RollingWindowDamageDescription = " seconds: "
  val CurrentDamageDescription = "Current Fight"

  val currentDamageLabel = new Label{text = CurrentDamageDescription; foreground = java.awt.Color.WHITE } // This label will be added to primary frame; value of text property will be updated when new data is available
  var currentDamageOutput = 0.0

  val windowPeriodsArray = windowPeriods.split(",")
  var windowPeriodsLabels = new Array[Label](windowPeriodsArray.length)
  for (i <- 0 to windowPeriodsArray.length - 1 ) {
    windowPeriodsLabels(i) = new Label{text = windowPeriodsArray(i) + RollingWindowDamageDescription; foreground = java.awt.Color.WHITE } // Each label will be added to primary frame; value of text property will be updated when new data is available
  }

  // This MainFrame is the primary frame representing the DamageMeter itself
  val mainFrame = new MainFrame {
    title = "EQLogParser - DamageMeter"
    contents = new GridPanel(1,2) {
      contents += currentDamageLabel
      background = new Color(40,0,0)
      contents += new GridPanel(windowPeriodsArray.length,1) {
        for (period <- windowPeriodsLabels ) {
          contents += period
        }
        background = new Color(40,0,0)
      }
      iconImage = toolkit.getImage("src\\main\\resources\\eq_logo.png")
    }
    size = new Dimension(500,300)
    centerOnScreen
  }

  /**
   * Re-calculate all damage values and update label text property values with new information
   */
  def updateOutputValues(): Unit = {
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
