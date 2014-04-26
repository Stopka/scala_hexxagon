package models.ais

import models.Game
import models.Field
import models.boards.maps.{FilterFieldRange, Map}
import scala.util.{Sorting}

/**
 * Created by stopka on 25.4.14.
 */
object Eniac extends AI{





  protected def evaluateMoves(board:Map,moves:Array[((Int,Int),(Int,Int))],player:Int,jump:Boolean)={
    for(move<-moves) yield {
      val (from,to)=move
      val (x,y)=to
      val point=if(jump){0}else{1}
      val metric=point+2*board.getNearFields(x,y).count(field=>field.getPlayer()>=0&&field.getPlayer()!=player)
      (metric,move)
    }
  }

  protected def selectMove(moves:Array[(Int,((Int,Int),(Int,Int)))])={
    Sorting.stableSort(moves,(a:(Int,((Int,Int),(Int,Int))),b:(Int,((Int,Int),(Int,Int))))=>a._1>b._1)
    if(Randomizer.getInt()<3){//Vyber jeden horších tahů s pravděpodobností
      val limit=5
      val pos=if(moves.length>=5){Randomizer.getInt(limit)}else{0}
      moves(pos)._2
    }else{//Vyber jeden z nejlepších tahů
      val best_moves=moves.filter(move=>moves(0)._1==move._1)
      best_moves(Randomizer.getInt(best_moves.length))._2
    }
  }
}
