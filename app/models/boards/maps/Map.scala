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

    def apply(x:Int,y:Int)={
      board(x)(y)
    }

    def getNearFields(x:Int,y:Int)={
      getFields().filter(nearFields(x,y))
    }

  def getFarFields(x:Int,y:Int)={
    getFields().filter(farFields(x,y))
  }

  def countPlayerFields(player:Int=(-2))={
    if(player==(-2)){
      getFields().length
    }else{
      getFields().count(field=>field.getPlayer()==player)
    }
  }
}
