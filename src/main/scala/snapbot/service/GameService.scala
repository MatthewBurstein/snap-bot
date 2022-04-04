package snapbot.service

import cats.effect.IO
import cats.implicits._
import snapbot.domain.{Card, Game, GameEnded, GameInProgress, GameState, MatchStrategy, Player, PlayerId}
import snapbot.utils.IOUtils.raceMany

class GameService(shufflerService: ShufflerService, randomNumberService: RandomNumberService) {
  // TODO parametrize this
  val numberOfPlayers = 2

  def create(numberOfDecks: Int, matchType: MatchStrategy): Game = {
    val cards: List[Card] =
      (1 to numberOfDecks).toList.flatMap { _ => Card.createDeck }

    val cardsPerPlayer = cards.length / numberOfPlayers

    val players = shufflerService.shuffle(cards)
      .grouped(cardsPerPlayer)
      .zipWithIndex
      .map { case (cards, playerIndex) => Player(PlayerId.fromInt(playerIndex), cards) }
      .toList

    Game(matchType, players, List.empty)
  }

  // TODO Players should be a cats NonEmptyList so that calling .head is safe
  def start(game: Game): IO[GameInProgress] = IO(GameInProgress(game, game.players.head.playerId))

  def play(state: GameInProgress): IO[GameState] = {
    val nextStateOpt = state.game.takeTurn(state.playerId)
    val nextPlayer = state.game.nextPlayer(state.playerId)
    nextStateOpt.map { nextState =>
      if (nextState.isMatch) {
        competeForPile(nextState).map {
          GameInProgress(_, nextPlayer.playerId)
        }
      } else {
        IO(GameInProgress(nextState, nextPlayer.playerId))
      }
    }.getOrElse(IO(GameEnded(nextPlayer)))
  }

  private def competeForPile(game: Game): IO[Game] = {
    val playerIOs: List[IO[(Player, Int)]] = game.players
      .map { player => createPlayerCompetitionIO(player) }

    raceMany(playerIOs)
      .flatTap { case (player, sleepTimeMillis) => printRoundWinner(player, sleepTimeMillis) }
      .map { case (player, _) => game.takeStack(player) }
  }

  private def createPlayerCompetitionIO(player: Player): IO[(Player, Int)] = {
    val sleepTimeMillis = randomNumberService.randomIntFrom0To100()
    println(s"${player.playerId} will take ${sleepTimeMillis}ms")
    IO {
      Thread.sleep(sleepTimeMillis)
      (player, sleepTimeMillis)
    }
  }

  private def printRoundWinner(player: Player, sleepTimeMillis: Int): IO[Unit] =
    IO.println(s"${player.playerId} took ${sleepTimeMillis}ms and won!")
}
