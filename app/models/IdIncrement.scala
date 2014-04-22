package models

/**
 * Created by stopka on 16.4.14.
 */
trait IdIncrement {
  var count=0
  def getNextId()={
    val id=count
    count+=1;
    id.toString.concat("_").concat(getRandomHash());
  }

  def getRandomHash()={
    Utils.randomString(10)
  }
}
