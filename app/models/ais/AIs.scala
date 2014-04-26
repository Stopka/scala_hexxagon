package models.ais

import models.Game
import models.boards.maps.FilterFieldRange

/**
 * Created by stopka on 25.4.14.
 */
object AIs{
  val ais=Map[String,AI]("AI"->Eniac)

  def apply(name:String,game:Game)={
    ais(name)(name,game)
  }

  def exists(name:String)={
    ais.contains(name)
  }
}
