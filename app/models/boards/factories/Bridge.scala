package models.boards.factories

import models.boards.maps

/**
  * Created by stopka on 23.4.14.
  */
object Bridge extends Factory{
   def apply()={
     new maps.Bridge()
   }
   def getName()={
     "Bridge"
   }
   def getImage()={
     "1"
   }
 }
