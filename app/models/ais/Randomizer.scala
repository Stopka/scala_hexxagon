package models.ais
import scala.util.Random
/**
 * Created by stopka on 27.4.14.
 */
object Randomizer {
  val random=Random
  def getInt(n:Int=100)={
    random.nextInt(n)
  }
}
