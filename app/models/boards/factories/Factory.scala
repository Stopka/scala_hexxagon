package models.boards.factories

/**
 * Created by stopka on 23.4.14.
 */
abstract class Factory {
  def apply():models.boards.maps.Map
  def getName():String
  def getImage():String
}
