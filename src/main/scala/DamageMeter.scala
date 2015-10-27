import scala.swing._

/** A module that displays damage per second information over various timeframes
  *
  * @param windowPeriods A string that represents the timeframes over which to display damage statistics
  * @param currentFightDelay The time that must elapse without damage occurring for a fight to be considered complete; if/when new damage occurs after this delay, the damage will contribute to a new "current fight" damage value
  */
class DamageMeter(windowPeriods: String, currentFightDelay: Int) {

  private val RollingWindowDamageDescription = "Seconds"
  private val CurrentDamageDescription = "Current Fight"

  private val currentDamageLabel = new Label{text = "<html><body style='text-align:center'>" + CurrentDamageDescription + "<br/><br/><div style='font-size:56px'>" + 0.0 + "</div></body></html>"; foreground = java.awt.Color.WHITE } // This label will be added to primary frame; value of text property will be updated when new data is available

  private val windowPeriodsArray = windowPeriods.split(",")
  private var windowPeriodsMap:Map[Int,Label] = Map()
  for (i <- 0 to windowPeriodsArray.length - 1 ) {
    windowPeriodsMap += (windowPeriodsArray(i).toInt -> new Label{text = "<html><body style='text-align:center'>" + windowPeriodsArray(i) + " " + RollingWindowDamageDescription  + "<br/><span style='font-size:14px'>" + 0.0 + "</span></body></html>"; foreground = java.awt.Color.WHITE })
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

  private var damageRecords = scala.collection.mutable.Map[Long,Int]() // Stores record of each occurrence of damage: [timestamp, damage amount]
  private val damagePattern = "(?:You|non-melee|taken)(?:\\s|\\w)*?(\\d+)(?:\\s|\\w)*damage".r // The regex pattern that will be used to identify damage in a log file line

  private var currentFightStart:Long = -1
  private var mostRecentDamage:Long = 0

  /** Process a new log file line. If line represents damage done, store amount and timestamp of damage, and update current fight timestamps. */
  def processNewLine(line: String): Unit = {

    val currentTimestamp: Long = System.currentTimeMillis / 1000

    for(matchDamage <- damagePattern.findAllIn(line).matchData) {
      addDamageRecord(currentTimestamp, matchDamage.group(1).toInt)
      mostRecentDamage = currentTimestamp
      if (currentFightStart == -1)
        currentFightStart = currentTimestamp
    }
  }

  /** Re-calculate all damage values and update label text values with new information */
  private def updateOutputValues(): Unit = {
    val currentTimestamp: Long = System.currentTimeMillis / 1000

    var damagePerSecondCalculationSum = 0

    if (currentTimestamp - mostRecentDamage > currentFightDelay) {
      currentFightStart = -1
      currentDamageLabel.text = "<html><body style='text-align:center'>" + CurrentDamageDescription + "<br/><br/><div style='font-size:56px'>" + 0.0 + "</div></body></html>"
    }
    else {
      damagePerSecondCalculationSum = 0
      for (damage <- damageRecords.filterKeys(_ >= currentFightStart).values) {
        damagePerSecondCalculationSum += damage
      }
      currentDamageLabel.text = "<html><body style='text-align:center'>" + CurrentDamageDescription + "<br/><br/><div style='font-size:56px'>" + "%.1f".format(damagePerSecondCalculationSum/(currentTimestamp-currentFightStart).toDouble).toDouble + "</div></body></html>"
    }

    for (period <- windowPeriodsArray) {
      damagePerSecondCalculationSum = 0
      for (damage <- damageRecords.filterKeys(currentTimestamp - _ < period.toInt).values) {
        damagePerSecondCalculationSum += damage
      }
      windowPeriodsMap(period.toInt).text = "<html><body style='text-align:center'>" + period  + " " + RollingWindowDamageDescription + "<br/><span style='font-size:14px'>" + "%.1f".format(damagePerSecondCalculationSum/period.toDouble).toDouble + "</span></body></html>"
    }
  }

  /** Store an occurrence of damage and associated timestamp */
  private def addDamageRecord(currentTimestamp: Long, damage: Int): Unit = {
    if (!damageRecords.contains(currentTimestamp)) {
      damageRecords.put(currentTimestamp, damage)
    }
    else {
      damageRecords(currentTimestamp) += damage
    }
    println(currentTimestamp + " | " + damage) // debug
  }

  /** Display the DamageMeter and begin it's execution */
  def launch(): Unit = {
    updateOutputValues()
    mainFrame.visible = true

    val updateThread = new Thread(new Runnable {
      def run() {
        while(true) {
          Thread.sleep(1000)
          updateOutputValues()
        }
      }
    })

    updateThread.start
  }
}
