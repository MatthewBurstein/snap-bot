package snapbot.domain

case class Game (
  matchStrategy: MatchStrategy,
  players:   List[Player],
  stack: List[Card]
) {
  def takeTurn(currentPlayerId: PlayerId): Option[Game] = {
    println(s"STACK: $stack")
    val (cardToPlayOpt, updatedPlayer) = getPlayer(currentPlayerId).takeTurn()
    cardToPlayOpt.map{ card =>
      println(s"CARD TO PLAY: $card")
      copy(
        players = updatePlayersById(currentPlayerId, updatedPlayer),
        stack = card +: stack
      )
    }
  }

  def isMatch: Boolean = stack match {
    case topCard :: secondCard :: _ => matchStrategy.isMatch(topCard, secondCard)
    case _ => false
  }

  def nextPlayer(currentPlayerId: PlayerId): Player = {
    val currentPlayerIndex = getPlayerIndex(currentPlayerId)
    if (currentPlayerIndex >= players.length - 1) players.head else players(currentPlayerIndex + 1)
  }

  def takeStack(player: Player): Game = {
    val updatedPlayer = player.copy(hand = player.hand ::: stack)
    copy(
      players = updatePlayersById(player.playerId, updatedPlayer),
      stack = List.empty
    )
  }

  private def getPlayer(playerId: PlayerId): Player =
    players.find(_.playerId == playerId).getOrElse(throw new RuntimeException("Unknown player id"))

  private def getPlayerIndex(playerId: PlayerId): Int = {
    val index = players.indexWhere(_.playerId == playerId)
      if (index >= 0) {
        index
      } else {
        throw new RuntimeException("Unknown player id")
      }
  }

  private def updatePlayersById(playerToUpdate: PlayerId, newValue: Player): List[Player] =
    players.map { player =>
      if (player.hasId(playerToUpdate)) newValue
      else player
    }
}
