package models.ais

import models.Game
import models.Field
import models.boards.maps.{FilterFieldRange, Map}
import scala.util.{Random, Sorting}

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
    val rand=Random
    val limit=5
    val pos=if(moves.length>=5){rand.nextInt(limit)}else{0}
    moves(pos)._2
  }
}
