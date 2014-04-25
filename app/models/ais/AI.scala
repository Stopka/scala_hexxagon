package models.ais

import models.Game

/**
 * Created by stopka on 25.4.14.
 */
abstract class AI {
  def apply(game:Game)
}
