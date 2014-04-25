package models

import models.boards.Boards
import scala.util.Sorting
import models.ais.AIs
import models.boards.maps.FilterFieldRange

/**
 * Created by stopka on 7.4.14.
 */
case class Game(val id:String,player1:String,val board_index:Int=0)extends FilterFieldRange{
  var players: Array[String] = Array(player1)
  var plays=0;
  //val player_limit=2;
  var selected=(-1,-1)
  val board=Boards(board_index)
  def getPlays()={
    plays
  }
  def getPlayerNumber(user:String)={
    if(isUserPlaying(user)){
      getPlays()
    }else{
      players.indexOf(user)
    }
  }

  def addUser(player:String,control:Boolean=true)={
   if(players.count(p=>p.equals(player))==0&&players.count(p=>true)<getPlayersMax()||(!control)){
      players=players:+player
    }
  }

  def isUserPlaying(user:String)={
    val p=getPlays()
    if(p<0){
      false
    }else{
      players(getPlays())==user
    }
  }

  def arePlayersReady()={
    countPlayers()==getPlayersMax();
  }

  def countPlayers()={
    players.count(p=>true)
  }

  def getPlayersMax()={
    board.getPlayerCount()
  }

  def getBoard()={
    board;
  }

  def isOnTurn(player:Int)={
    getPlays()==player
  }

  def click(user:String,x:Int,y:Int){
    val player=getPlayerNumber(user)
     if(!isOnTurn(player)){
       return
     }
    try {
      val field = board(x,y)
      if(field.getPlayer()==player) {
        val sel = field.isSelected()
        unselectAll()
        if (!sel) {
          select(x, y)
        }
      }else{
        field.getMark() match{
          case 0=>unselectAll()
          case 1=>unselectAll()
          case 2=>{
            split(x,y)
            doRest(x,y)
          }
          case 3=>{
            jump(x,y)
            doRest(x,y)
          }
        }
      }
    }catch {
      case e:ArrayIndexOutOfBoundsException=>unselectAll()
    }
  }

  private def doRest(x:Int,y:Int){
    claim(x,y)
    unselectAll()
    if(!claimEnd()){
      nextPlayer()
      tryAI()
    }
  }

  private def tryAI(){
    val aiId=players(getPlays());
    if(AIs.exists(aiId)) {
      AIs(players(getPlays()),this)
    }
  }

  private def nextPlayer(decrement:Int=getPlayersMax()){
    plays=(plays+1)%getPlayersMax()
    if(!isPlayerMovable(plays)&&decrement>0){
      nextPlayer(decrement-1)
    }
  }

  private def isPlayerMovable(player:Int)={
    val fields=board.getFields()
    if(fields.count(field=>field.getPlayer()==player&&fields.filter(farFields(field.x,field.y)).count(field=>field.getPlayer()<0)>0)==0){
      false
    }else{
      true
    }
  }

  private def unselectAll(){
    selected=(-1,-1)
    for(field<-board.getFields()){
      field.removeMarks()
    }
  }

  private def split(x:Int,y:Int){
    val from=board(selected._1,selected._2)
    board(x,y).setPlayer(from.getPlayer())
  }

  private def jump(x:Int,y:Int){
    var from=board(selected._1,selected._2)
    board(x,y).setPlayer(from.getPlayer())
    from.setPlayer()
  }

  private def claim(x:Int,y:Int){
    val from=board(x,y)
    for(field<-board.getFields().filter(nearFields(x,y))){
      if(field.getPlayer()>=0){
        field.setPlayer(from.getPlayer());
      }
    }
  }

  private def select(x:Int,y:Int){
    selected=(x,y)
    for(field<-board.getFarFields(x,y).filter(field=>field.getPlayer()<0)){
      field.markJump()
    }
    for(field<-board.getNearFields(x,y).filter(field=>field.getPlayer()<0)){
      field.markSplit()
    }
    board(x,y).select();
  }

  def getScore(player:Int=(-2))={
    board.countPlayerFields(player)
  }

  def isOver()={
    board.getFields().count(field=>field.getPlayer()==(-1))==0
  }

  @deprecated
  private def getLooser():Int={
    for(player<-0 to players.length-1){
      if(!isPlayerMovable(player)){
        return player;
      }
    }
    return -1
  }

  def getWinnerPlayer()={
    var list=Array[Tuple2[Int,Int]]();
    for(i<-0 to getPlayersMax()-1){
      list :+= Tuple2[Int,Int](i,getScore(i))
    }
    list=Sorting.stableSort(list,(a:Tuple2[Int,Int],b:Tuple2[Int,Int])=>a._2 > b._2)
    if(list(0)._2==list(1)._2){
      (-1)
    }else{
      list(0)._1
    }
  }

  def claimEnd():Boolean={
    if(getPlayersMax()>2){
      return isOver();
    }
    val looser=getLooser();
    if(looser>=0){
      plays=(-2);
      for(field<-board.getFields().filter(field=>field!=null&&field.getPlayer()<0)){
        field.setPlayer((looser+1)%getPlayersMax())
      }
    }
    looser>=0
  }

  def isTimer(player:Int)={
    !isOnTurn(player)&& !isOver()
  }

}




