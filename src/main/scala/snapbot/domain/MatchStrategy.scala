package snapbot.domain

sealed trait MatchStrategy
case object MatchSuit extends MatchStrategy
case object MatchValue extends MatchStrategy
case object MatchBoth extends MatchStrategy

object MatchStrategy {
  def fromString(str: String): MatchStrategy = str match {
    case "s" => MatchSuit
    case "v" => MatchValue
    case "b" => MatchBoth
    case _ => throw new RuntimeException("unknown match strategy")
  }
}
