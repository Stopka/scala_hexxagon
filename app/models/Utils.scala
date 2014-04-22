package models

import scala.util.Random

/**
 * Created by stopka on 16.4.14.
 */
object Utils {
  def randomString(n: Int) = {
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
    val sb = new StringBuilder
    for (i <- 1 to n) {
      val randomNum = Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }
}
