package models.boards.maps

import models.Field

/**
 * Created by stopka on 20.4.14.
 */
class Matrix extends Map {
  def getPlayerCount() = 2

  protected def create() = Array.tabulate[Field](9, 9) {
    (x, y) => {
      if ((x == 0 && y == 4) || (x == 8 && y == 0) || (x == 4 && y == 8)) {
        new Field(x, y, 0)
      } else {
        if ((x == 4 && y == 0) || (x == 8 && y == 4) || (x == 0 && y == 8)) {
          new Field(x, y, 1)
        } else {
          new Field(x, y, -1)
        }
      }
    }
  }
}
