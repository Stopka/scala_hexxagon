package models.boards.maps

import models.Field

/**
 * Created by stopka on 20.4.14.
 */
abstract class Map {
  def getPlayerCount():Int;
  protected def create():Array[Array[Field]];

    val board=create()
    val fields=linearize()

    def getFields()={
      fields
    }

    def getFieldArray()={
      board
    }

    private def linearize(i:Int=board.length-1):Array[Field]={
      if(i==0){
        board(i);
      }else {
        Array.concat(board(i), linearize(i - 1))
      }
    }

    def apply(x:Int,y:Int)={
      board(x)(y)
    }
}
