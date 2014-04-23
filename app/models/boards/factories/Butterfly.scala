package models.boards.factories

import models.boards.maps

/**
  * Created by stopka on 23.4.14.
  */
object Butterfly extends Factory{
   def apply()={
     new maps.Butterfly()
   }
   def getName()={
     "Butterfly"
   }
 }
