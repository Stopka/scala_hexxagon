package models.boards.maps

import models.Field

/**
 * Created by stopka on 20.4.14.
 */
abstract class Map extends FilterFieldRange{
  def getPlayerCount():Int;
  protected def create():Array[Array[Field]];

    val board=create()
    val fields=linearize().filter(field=>field!=null)

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

    def apply(point:(Int,Int))={
      val (x,y)=point;
      board(x)(y)
    }

    def getNearFields(point: (Int,Int))={
      getFields().filter(nearFields(point))
    }

  def getFarFields(point: (Int,Int))={
    getFields().filter(farFields(point))
  }

  def countPlayerFields(player:Int=(-2))={
    if(player==(-2)){
      getFields().length
    }else{
      getFields().count(field=>field.getPlayer()==player)
    }
  }
}
