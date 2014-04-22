package models

/**
 * Created by stopka on 17.4.14.
 */
object Games extends IdIncrement{
  val games=scala.collection.mutable.Map[String,Game]()

  def add(user:String)={
    val res=getNextId()
    games(res)= Game(res,user)
    res
  }
  def get(game:String)={
    games(game)
  }

  def apply(game:String)={
    get(game)
  }
}
