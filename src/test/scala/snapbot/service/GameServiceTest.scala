package snapbot.service

import org.mockito.Mockito.when
import snapbot.domain._
import snapbot.utils.UnitTest

class GameServiceTest extends UnitTest {
  val numberOfCardsInDeck = CardValues.all.length * Suits.all.length
  val numberOfDecks = 2
  val matchType = MatchSuit

  val mockShufflerService: ShufflerService = mock[ShufflerService]
  val gameService: GameService = new GameService(mockShufflerService)

  override def beforeEach(): Unit = {
    super.beforeEach()

    reset(mockShufflerService)
    when(mockShufflerService.shuffle(*)).thenAnswer(call => call.getArgument(0).asInstanceOf[List[Card]])
  }

  "create" should {
    "construct a game with 2 players" in {
      val game = gameService.create(numberOfDecks, matchType)

      game.players.length shouldBe 2
    }

    "construct a game with empty piles" in {
      val game = gameService.create(numberOfDecks, matchType)

      val allPiles: List[Card] = game.players.flatMap(_.pile)
      allPiles.length shouldBe 0
    }

    "construct a game with equal sized shuffled stacks of cards from given number of decks" in {
      val game = gameService.create(numberOfDecks, matchType)

      val allPossibleCards = for {
        suit <- Suits.all
        value <- CardValues.all
      } yield Card(value, suit)

      val allCardsInGame: List[Card] = game.players.flatMap(_.stack)
      allPossibleCards.foreach(card => allCardsInGame.count(_ == card) shouldBe numberOfDecks)

      mockShufflerService.shuffle(*) wasCalled once
    }

    "construct a game with the given MatchSuit" in {
      val game = gameService.create(numberOfDecks, matchType)

      game.matchType shouldBe MatchSuit
    }
  }
}
