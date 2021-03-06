package models.ais

import models.{Field, Game}
import models.boards.maps.{FilterFieldRange, Map}

/**
 * Created by stopka on 25.4.14.
 */
abstract class AI  extends FilterFieldRange {
  protected def evaluateMoves(board:Map,moves:Array[((Int,Int),(Int,Int))],player:Int,jump:Boolean):Array[(Int,((Int,Int),(Int,Int)))]
  protected def selectMove(moves:Array[(Int,((Int,Int),(Int,Int)))]):((Int,Int),(Int,Int))

  def apply(name:String,game:Game)={
    val (from,to)=findMove(game)
    game.click(name,from)
    game.click(name,to)
  }

  private def findMove(game:Game)={
    val player=game.getPlays()
    val board=game.getBoard()
    val myFields=board.getFields().filter(field=>field.getPlayer()==player)
    val split=getPossibleMoves(board,myFields,splits)
    val jump=getPossibleMoves(board,myFields,jumps)
    val moves=Array.concat(evaluateMoves(board,split,player,false),evaluateMoves(board,jump,player,true))
    selectMove(moves)
  }

  private def getPossibleMoves(board:Map,fields:Array[Field],method:(Map,(Int,Int))=>Array[Field]):Array[((Int,Int),(Int,Int))]={
    val fieldFrom=fields.head
    val from=(fieldFrom.x,fieldFrom.y)
    val moves=for(field<-method(board,from))
    yield (from,(field.x,field.y))
    if(fields.length>1){
      Array.concat(moves,getPossibleMoves(board,fields.tail,method))
    }else{
      moves
    }
  }

  private def splits(board:Map,from:(Int,Int))={
    val (x,y)=from
    board.getNearFields(from).filter(field=>field.getPlayer()==(-1)&&field.x!=x&&field.y!=y)
  }

  private def jumps(board:Map,from:(Int,Int))={
    board.getFarFields(from).filterNot(nearFields(from)).filter(field=>field.getPlayer()==(-1))
  }
}
