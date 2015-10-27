import org.apache.commons.io.input.TailerListenerAdapter

/** Handles a new line from the Tailer.
  *
  * @param damageMeter The damage meter that should process the new line and display resulting information
  */
class LogTailer(damageMeter: DamageMeter) extends TailerListenerAdapter {
  override def handle(line: String): Unit = {
    damageMeter.processNewLine(line)
  }
}
