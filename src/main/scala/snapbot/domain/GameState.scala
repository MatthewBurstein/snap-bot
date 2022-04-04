package snapbot.domain


sealed trait GameState

case class GameInProgress(game: Game, playerId: PlayerId) extends GameState

case class GameEnded(winner: Player) extends GameState
