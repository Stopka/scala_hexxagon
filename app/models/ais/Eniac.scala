package models.ais

import models.Game
import models.Field
import models.boards.maps.{FilterFieldRange, Map}

/**
 * Created by stopka on 25.4.14.
 */
object Eniac extends AI with FilterFieldRange{

  def findMove(game:Game)={
    val player=game.getPlays()
    val board=game.getBoard()
    val myFields=board.getFields().filter(field=>field.getPlayer()==player)
    val split=getPossibleSplits(board,myFields,splits)
    val jump=getPossibleSplits(board,myFields,jumps)
    split.head
  }

  private def getPossibleSplits(board:Map,fields:Array[Field],method:(Map,(Int,Int))=>Array[Field]):Array[((Int,Int),(Int,Int))]={
    val fieldFrom=fields.head
    val from=(fieldFrom.x,fieldFrom.y)
    val moves=for(field<-method(board,from))
        yield (from,(field.x,field.y))
    if(fields.length>1){
      Array.concat(moves,getPossibleSplits(board,fields.tail,method))
    }else{
      moves
    }
  }

  private def splits(board:Map,from:(Int,Int))={
    board.getNearFields(from._1,from._2).filter(field=>field.getPlayer()==(-1)&&field.x!=from._1&&field.y!=from._2)
  }

  private def jumps(board:Map,from:(Int,Int))={
    board.getFarFields(from._1,from._2).filterNot(nearFields(from._1,from._2)).filter(field=>field.getPlayer()==(-1))
  }
}
