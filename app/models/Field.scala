package models

/**
 * Created by stopka on 20.4.14.
 */
class Field(val x:Int,val y:Int,var player:Int) {
  var mark=0
  def setPlayer(player:Int=(-1))={
    this.player=player
  }

  def getMark()={
    mark
  }

  def getPlayer()={
    this.player
  }

  def isUnmarked()={
    this.mark==0
  }

  def isSelected()={
    this.mark==1
  }

  def isSplitMarked()={
    this.mark==2
  }

  def isJumpMarked()={
    this.mark==3
  }

  def removeMarks()={
    mark=0
  }

  def select()={
    mark=1
  }

  def markSplit()={
    mark=2
  }

  def markJump()={
    mark=3
  }
}
