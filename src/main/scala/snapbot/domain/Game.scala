package snapbot.domain

case class Game (
  matchType: MatchStrategy,
  players:   List[Player]
)
