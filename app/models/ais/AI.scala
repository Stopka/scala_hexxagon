package models.ais

import models.Game

/**
 * Created by stopka on 25.4.14.
 */
abstract class AI {
  def findMove(game:Game):((Int,Int),(Int,Int))
  def apply(name:String,game:Game)={
    val (from,to)=findMove(game)
    game.click(name,from._1,from._2)
    game.click(name,to._1,to._2)
  }
}
