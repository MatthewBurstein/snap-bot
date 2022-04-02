package snapbot.domain

case class Player private (
  playerId: PlayerId,
  stack:    List[Card],
  pile:     List[Card]
) {
  def takeTurn(): Player = stack match {
    case Nil => this //TODO what should happen here
    case first :: tail => copy(stack = tail, pile = first +: pile)
  }
}

object Player {
  def apply(playerId: PlayerId, stack: List[Card]): Player = Player(playerId, stack, List.empty)
}

case class PlayerId(value: String)

object PlayerId {
  def fromInt(int: Int): PlayerId = PlayerId(s"Player $int")
}

