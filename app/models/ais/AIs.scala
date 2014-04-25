package models.ais

/**
 * Created by stopka on 25.4.14.
 */
object AIs {
  val ais=Map[String,AI]("AI"->Eniac)

  def apply(name:String)={
    ais(name)
  }

  def exists(name:String)={
    ais.contains(name)
  }
}
