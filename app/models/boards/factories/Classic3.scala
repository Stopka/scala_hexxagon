package models.boards.factories

import models.boards.maps

/**
 * Created by stopka on 23.4.14.
 */
object Classic3 extends Factory{
  def apply()={
    new maps.Classic3()
  }
  def getName()={
    "Classic3"
  }
  def getImage()={
    "classic3"
  }
  def getPlayers()={
    "3"
  }
}
