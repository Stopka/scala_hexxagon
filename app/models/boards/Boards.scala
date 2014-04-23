package models.boards

import models.boards.factories.Factory
import factories._
/**
 * Created by stopka on 23.4.14.
 */
object Boards {
  val factories=Array[Factory](Classic)
  def apply(index:Int)={
    factories(index)()
  }
}
