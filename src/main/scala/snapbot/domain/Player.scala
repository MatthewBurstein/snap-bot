package snapbot.domain

case class Player private (
                            playerId: PlayerId,
                            hand:    List[Card]
) {
  def takeTurn(): (Option[Card], Player) = {
    println(s"HAND SIZE: $playerId ${hand.length}")
    hand match {
      case Nil => (None, this)
      case firstCard :: rest => (Some(firstCard), copy(hand = rest))
    }
  }

  def hasId(testPlayerId: PlayerId): Boolean = playerId == testPlayerId
}

case class PlayerId(value: String)

object PlayerId {
  def fromInt(int: Int): PlayerId = PlayerId(s"Player $int")
}

