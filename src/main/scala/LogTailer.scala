//package main.scala
import org.apache.commons.io.input.TailerListenerAdapter

/**
 * Created by Taylor on 7/2/2015.
 */
class LogTailer(damageMeter: DamageMeter) extends TailerListenerAdapter {
  override def handle(line: String): Unit = {
    damageMeter.processNewLine(line)
  }
}
