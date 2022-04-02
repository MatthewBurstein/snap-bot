package snapbot.service

import snapbot.domain.{Card, Game, MatchStrategy, Player, PlayerId}

class GameService(shufflerService: ShufflerService) {
  // TODO parametrize this
  val numberOfPlayers = 2

  def create(numberOfDecks: Int, matchType: MatchStrategy): Game = {
    val cards: List[Card] =
      (1 to numberOfDecks).toList.flatMap { _ => Card.createDeck }

    val cardsPerPlayer = cards.length / numberOfPlayers

    val players = shufflerService.shuffle(cards)
      .grouped(cardsPerPlayer)
      .zipWithIndex
      .map{ case (cards, playerIndex) => Player(PlayerId.fromInt(playerIndex), cards) }
      .toList

    Game(matchType, players)
  }
}
