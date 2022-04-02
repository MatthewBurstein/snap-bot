package snapbot.service

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import snapbot.domain.{Game, MatchSuit}
import snapbot.utils.UnitTest

import java.io.StringReader

class GameInitializerServiceTest extends UnitTest {

  val gameInitializerService = new GameInitializerService()

  "GameInitializerService" should {
    "consume input and construct game with appropriate number of decks and matchType" in {
      val numberOfDecksInput = "2"
      val matchTypeInput = "s"
      val input = new StringReader(List(numberOfDecksInput, matchTypeInput).mkString("\n"))

      var game: Game = null

      Console.withIn(input) {
        game = gameInitializerService.initialize()
      }

      game shouldBe Game(2, MatchSuit)
    }

    // TODO test when inputs are not valid
  }
}
