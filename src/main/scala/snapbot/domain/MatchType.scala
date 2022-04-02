package snapbot.domain

sealed trait MatchType
case object MatchSuit extends MatchType
case object MatchValue extends MatchType
case object MatchBoth extends MatchType

object MatchType {
  def fromString(str: String): MatchType = str match {
    case "s" => MatchSuit
    case "v" => MatchValue
    case "b" => MatchBoth
    case _ => throw new RuntimeException("unknown match type")
  }
}
