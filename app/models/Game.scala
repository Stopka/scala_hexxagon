package models
/**
 * Created by stopka on 7.4.14.
 */
case class Game(val id:String,player1:String){
  var players: Array[String] = Array(player1)
  var plays=0;
  val player_limit=2;
  var selected=(-1,-1)
  val board:Array[Array[Field]] = Array.tabulate[Field](9,9){
    (x,y)=>{
      if(x+y<=3||x+y>=13||(x==4&&y==3)||(x==3&&y==5)||(x==5&&y==4)){
        null
      }else{
        if((x==0&&y==4)||(x==8&&y==0)||(x==4&&y==8)) {
          new Field(x,y,0)
        }else{
          if((x==4&&y==0)||(x==8&&y==4)||(x==0&&y==8)) {
            new Field(x,y,1)
          }else{
            new Field(x,y,-1)
          }
        }
      }
    }
  }
  def getPlays()={
    plays
  }
  def getPlayerNumber(user:String)={
    players.indexOf(user)
  }

  def addUser(player:String)={
   if(players.count(p=>p.equals(player))==0&&players.count(p=>true)<getPlayersMax()){
      players=players:+player
    }
  }

  def isUserPlaying(user:String)={
    players(plays)==user
  }

  def arePlayersReady()={
    countPlayers()==getPlayersMax();
  }

  def countPlayers()={
    players.count(p=>true)
  }

  def getPlayersMax()={
    player_limit
  }

  def getBoard()={
    board;
  }

  def isOnTurn(player:Int)={
    plays==player
  }

  def click(user:String,x:Int,y:Int){
    val player=getPlayerNumber(user)
     if(!isOnTurn(player)){
       return
     }
    try {
      val field = board(x)(y)
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
            claim(x,y)
            if(!claimEnd()){
              nextPlayer()
            }
            unselectAll()
          }
          case 3=>{
            jump(x,y)
            claim(x,y)
            if(!claimEnd()){
              nextPlayer()
            }
            unselectAll()
          }
        }
      }
    }catch {
      case e:ArrayIndexOutOfBoundsException=>unselectAll()
    }
  }

  private def nextPlayer(){
    plays=(plays+1)%player_limit
  }

  private def unselectAll(){
    selected=(-1,-1)
    for(field<-linearize().filter(field=>field!=null)){
      field.removeMarks()
    }
  }

  private def split(x:Int,y:Int){
    val from=board(selected._1)(selected._2)
    board(x)(y).setPlayer(from.getPlayer())
  }

  private def jump(x:Int,y:Int){
    var from=board(selected._1)(selected._2)
    board(x)(y).setPlayer(from.getPlayer())
    from.setPlayer()
  }

  private def claim(x:Int,y:Int){
    val from=board(x)(y)
    for(field<-linearize().filter(nearFields(x,y))){
      if(field.getPlayer()>=0){
        field.setPlayer(from.getPlayer());
      }
    }
  }

  private def select(x:Int,y:Int){
    selected=(x,y)
    for(field<-linearize().filter(farFields(x,y))){
      if(field.getPlayer()<0) {
        field.markJump()
      }
    }
    for(field<-linearize().filter(nearFields(x,y))){
      if(field.getPlayer()<0) {
        field.markSplit()
      }
    }
    board(x)(y).select();
  }

  private def linearize(i:Int=board.length-1):Array[Field]={
    if(i==0){
      board(i);
    }else {
      Array.concat(board(i), linearize(i - 1))
    }
  }

  private def nearFields(x:Int,y:Int)={
    field:Field=>field!=null&&Math.abs(x-field.x)<=1&&Math.abs(y-field.y)<=1&&Math.abs((y-field.y)+(x-field.x))!=2
  }

  private def farFields(x:Int,y:Int)={
    field:Field=>field!=null&&((Math.abs(x-field.x)<=1&&Math.abs(y-field.y)<=1&&Math.abs((y-field.y)+(x-field.x))==2)||(Math.abs(x-1-field.x)<=1&&Math.abs(y+1-field.y)<=1)||(Math.abs(x+1-field.x)<=1&&Math.abs(y-1-field.y)<=1))
  }

  def getScore(player:Int=(-2))={
    if(player==(-2)){
      linearize().count(field=>field!=null)
    }else{
      linearize().count(field=>field!=null&&field.getPlayer()==player)
    }
  }

  def isOver()={
    getLooser()>=0
  }

  private def getLooser():Int={
    for(player<-0 to players.length-1){
      val fields=linearize()
      if(fields.count(field=>field!=null&&field.getPlayer()==player&&fields.filter(farFields(field.x,field.y)).count(field=>field.getPlayer()<0)>0)==0){
        return player;
      }
    }
    return -1
  }

  def getWinnerPlayer()={
    if(getScore(0)==getScore(1)){
      (-1)
    }else{
      if(getScore(0)>getScore(1)){
        0
      }else{
        1
      }
    }
  }

  def claimEnd()={
    val looser=getLooser();
    if(looser>=0){
      plays=(-2);
      for(field<-linearize().filter(field=>field!=null&&field.getPlayer()<0)){
        field.setPlayer((looser+1)%player_limit)
      }
    }
    looser>=0
  }

  def isTimer(player:Int)={
    !isOnTurn(player)&& !isOver()
  }

}




