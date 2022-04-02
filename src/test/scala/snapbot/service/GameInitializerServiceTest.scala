package snapbot.service

import org.mockito.Mockito.when
import snapbot.domain._
import snapbot.utils.UnitTest

import java.io.StringReader

class GameInitializerServiceTest extends UnitTest {

  val gameServiceMock = mock[GameService]
  val gameInitializerService = new GameInitializerService(gameServiceMock)

  override def beforeEach(): Unit = {
    reset(gameServiceMock)
    super.beforeEach()
  }

  "GameInitializerService" should {
    "consume input and construct game with appropriate number of decks and matchType" in {
      val cards1 = List(Card(CardValues.Ace, Suits.Hearts))
      val cards2 = List(Card(CardValues.Two, Suits.Diamonds))
      val expectedGame = Game(MatchSuit, List(Player(PlayerId("player1"), cards1), Player(PlayerId("player2"), cards2)))

      val numberOfDecksInput = "2"
      val matchTypeInput = "s"
      val input = new StringReader(List(numberOfDecksInput, matchTypeInput).mkString("\n"))

      when(gameServiceMock.create(*, *)).thenReturn(expectedGame)

      var actualGame: Game = null

      Console.withIn(input) {
        actualGame = gameInitializerService.initialize()
      }

      actualGame shouldBe expectedGame
      gameServiceMock.create(2, MatchSuit) wasCalled once
    }

    // TODO test when inputs are not valid
  }
}
