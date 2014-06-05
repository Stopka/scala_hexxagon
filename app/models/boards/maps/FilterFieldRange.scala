package models.boards.maps

import models.Field

/**
 * Created by stopka on 25.4.14.
 */
trait FilterFieldRange {
  protected def nearFields(point:(Int,Int))={
    val (x,y)=point;
    field:Field=>Math.abs(x-field.x)<=1&&Math.abs(y-field.y)<=1&&Math.abs((y-field.y)+(x-field.x))!=2
  }

  protected def farFields(point:(Int,Int))={
    val (x,y)=point;
    field:Field=>((Math.abs(x-field.x)<=1&&Math.abs(y-field.y)<=1&&Math.abs((y-field.y)+(x-field.x))==2)||(Math.abs(x-1-field.x)<=1&&Math.abs(y+1-field.y)<=1)||(Math.abs(x+1-field.x)<=1&&Math.abs(y-1-field.y)<=1))
  }
}
