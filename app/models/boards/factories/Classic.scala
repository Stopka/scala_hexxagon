package models.boards.factories

import models.boards.maps

/**
 * Created by stopka on 23.4.14.
 */
object Classic extends Factory{
  def apply()={
    new maps.Classic()
  }
  def getName()={
    "Classic"
  }
}
