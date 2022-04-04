package snapbot

import cats.effect.{ExitCode, IO, IOApp}
import snapbot.domain.{GameEnded, GameInProgress, GameState}
import snapbot.service.{GameInitializerService, GameService, RandomNumberService, ShufflerService}

object Main extends IOApp {
  val gameService = new GameService(new ShufflerService, new RandomNumberService)
  val gameInitializerService = new GameInitializerService(gameService)

  val game = gameInitializerService.initialize()

  override def run(args: List[String]): IO[ExitCode] =
    runGame(gameService.start(game))
      .map(state => println(s"WINNER: ${state.winner.playerId}"))
      .map(_ => ExitCode.Success)

  def runGame(gameStateIo: IO[GameInProgress]): IO[GameEnded] =
    gameStateIo.flatMap { gameState =>
      gameService.play(gameState).flatMap {
        case state: GameInProgress => runGame(IO.pure(state))
        case state: GameEnded => IO.pure(state)
      }
    }
}
