package models

import java.util.Date
import scala.util.Random


object Users extends IdIncrement{
  def add()={
    getNextId()
  }

  def apply()={
    add()
  }
}


