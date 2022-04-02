package snapbot

import snapbot.service.GameInitializerService

object Main extends App {
  val gameInitializerService = new GameInitializerService

  val game = gameInitializerService.initialize()
}
