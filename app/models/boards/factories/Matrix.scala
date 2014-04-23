package models.boards.factories

import models.boards.maps

/**
  * Created by stopka on 23.4.14.
  */
object Matrix extends Factory{
   def apply()={
     new maps.Matrix()
   }
   def getName()={
     "Matrix"
   }
 }
