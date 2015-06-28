/**
 * Created by Taylor Ellison on 6/28/2015.
 */
import scala.swing._

class DamageMeter {

  val CurrentDamageDescription = "Current Fight"
  val FiveSecondDamageDescription  = "5 seconds: "
  val TenSecondDamageDescription = "10 seconds: "
  val TwentySecondDamageDescription  = "20 seconds: "
  val FortySecondDamageDescription= "40 seconds: "

  var currentDamageOutput = 0.0
  var fiveSecondDamageOutput = 0.0
  var tenSecondDamageOutput = 0.0
  var twentySecondDamageOutput = 0.0
  var fortySecondDamageOutput = 0.0

  val currentDamageLabel = new Label{text = CurrentDamageDescription + currentDamageOutput; foreground = java.awt.Color.WHITE }
  val fiveSecondDamageLabel = new Label{text = FiveSecondDamageDescription + fiveSecondDamageOutput; foreground = java.awt.Color.WHITE }
  val tenSecondDamageLabel = new Label{text = TenSecondDamageDescription + tenSecondDamageOutput; foreground = java.awt.Color.WHITE }
  val twentySecondDamageLabel = new Label{text = TwentySecondDamageDescription + twentySecondDamageOutput; foreground = java.awt.Color.WHITE }
  val fortySecondDamageLabel = new Label{text = FortySecondDamageDescription + fortySecondDamageOutput; foreground = java.awt.Color.WHITE }

  val mainFrame = new MainFrame {
    title = "EQLogParser - DamageMeter"
    contents = new GridPanel(1,2) {
      contents += currentDamageLabel
      background = new Color(40,0,0)
      contents += new GridPanel(4,1) {
        contents += fiveSecondDamageLabel
        contents += tenSecondDamageLabel
        contents += twentySecondDamageLabel
        contents += fortySecondDamageLabel
        background = new Color(40,0,0)
      }
      iconImage = toolkit.getImage("assets\\eq_logo.png")
    }
    size = new Dimension(500,300)
    centerOnScreen
  }

  def updateOutputValues(): Unit = {
    currentDamageLabel.text = "<html><body style='text-align:center'>" + CurrentDamageDescription + "<br/><br/><div style='font-size:56px'>" + currentDamageOutput + "</div></body></html>"
    fiveSecondDamageLabel.text = "<html><body>" + FiveSecondDamageDescription + "<span style='font-size:14px'>" + fiveSecondDamageOutput + "</span></body></html>"
    tenSecondDamageLabel.text = "<html><body>" + TenSecondDamageDescription + "<span style='font-size:14px'>" + tenSecondDamageOutput + "</span></body></html>"
    twentySecondDamageLabel.text = "<html><body>" + TwentySecondDamageDescription + "<span style='font-size:14px'>" + twentySecondDamageOutput + "</span></body></html>"
    fortySecondDamageLabel.text = "<html><body>" + FortySecondDamageDescription + "<span style='font-size:14px'>" + fortySecondDamageOutput + "</span></body></html>"
  }

  def launch(): Unit = {
    updateOutputValues()
    mainFrame.visible = true
    currentDamageOutput = 20.4
    fiveSecondDamageOutput = 22.7
    tenSecondDamageOutput = 22.8
    twentySecondDamageOutput = 21.3
    fortySecondDamageOutput = 19.6
    updateOutputValues()
  }
}
