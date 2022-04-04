package snapbot.service

import scala.util.Random

class RandomNumberService {
  def randomIntFrom0To100(): Int = (Random.nextFloat()* 100).toInt
}
