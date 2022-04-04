package snapbot.domain

sealed trait MatchStrategy {
  def isMatch(card1: Card, card2: Card): Boolean
}
case object MatchSuit extends MatchStrategy {
  def isMatch(card1: Card, card2: Card): Boolean = card1.suit == card2.suit
}
case object MatchValue extends MatchStrategy {
  def isMatch(card1: Card, card2: Card): Boolean = card1.value == card2.value
}
case object MatchSuitAndValue extends MatchStrategy {
  def isMatch(card1: Card, card2: Card): Boolean =
    card1.value == card2.value && card2.suit == card2.suit
}

object MatchStrategy {
  def fromString(str: String): MatchStrategy = str match {
    case "s" => MatchSuit
    case "v" => MatchValue
    case "b" => MatchSuitAndValue
    case _ => throw new RuntimeException("unknown match strategy")
  }
}
