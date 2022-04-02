package snapbot

import snapbot.service.{GameInitializerService, GameService, ShufflerService}

object Main extends App {
  val shufflerService = new ShufflerService
  val gameService = new GameService(shufflerService)
  val gameInitializerService = new GameInitializerService(gameService)

  val game = gameInitializerService.initialize()
}
