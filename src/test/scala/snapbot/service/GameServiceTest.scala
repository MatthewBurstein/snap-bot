package snapbot.service

import org.mockito.Mockito.when
import snapbot.domain.CardValues.{Ace, Queen}
import snapbot.domain.Suits.{Clubs, Diamonds, Hearts, Spades}
import snapbot.domain._
import snapbot.utils.UnitTest

class GameServiceTest extends UnitTest {
  val numberOfCardsInDeck = CardValues.all.length * Suits.all.length
  val numberOfDecks = 2
  val matchType = MatchSuit

  val mockRandomNumberService: RandomNumberService = mock[RandomNumberService]
  val mockShufflerService: ShufflerService = mock[ShufflerService]
  val gameService: GameService = new GameService(mockShufflerService, mockRandomNumberService)

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

    "construct a game with equal sized shuffled hands of cards from given number of decks" in {
      val game = gameService.create(numberOfDecks, matchType)

      val allPossibleCards = for {
        suit <- Suits.all
        value <- CardValues.all
      } yield Card(value, suit)

      val allCardsInGame: List[Card] = game.players.flatMap(_.hand)
      allPossibleCards.foreach(card => allCardsInGame.count(_ == card) shouldBe numberOfDecks)

      mockShufflerService.shuffle(*) wasCalled once
    }

    "construct a game with the given MatchSuit" in {
      val game = gameService.create(numberOfDecks, matchType)

      game.matchStrategy shouldBe MatchSuit
    }
  }

  "play" should {
    val playerWithNoCardsInHand = Player(
      PlayerId("playerWithNoCardsInHand"),
      List.empty
    )

    val playerWithCards1 = Player(
      PlayerId("playerWithCards1"),
      List(Card(Ace, Spades), Card(Queen, Diamonds))
    )

    val playerWithCards2 = Player(
      PlayerId("playerWithCards2"),
      List(Card(Ace, Hearts), Card(Queen, Clubs))
    )

    val playerWithMatch = Player(
      PlayerId("playerWithMatch"),
      List(Card(Queen, Clubs), Card(Ace, Spades))
    )

    val stack = List(Card(Queen, Clubs))

    "return the winning player when a player has no cards left" in {
      val game = Game(
        MatchSuitAndValue,
        List(
          playerWithNoCardsInHand,
          playerWithCards1
        ),
        stack
      )

      val result = gameService.play(GameInProgress(game, playerWithNoCardsInHand.playerId))

      result.unsafeRunSync() shouldBe GameEnded(playerWithCards1)
    }

    "take a turn when no player has won and there is no match" in {
      val game = Game(
        MatchSuitAndValue,
        List(
          playerWithCards2,
          playerWithCards1
        ),
        stack
      )

      val result = gameService.play(GameInProgress(game, playerWithCards1.playerId))

      val expectedUpdatedPlayer1 = Player(
        playerWithCards1.playerId,
        List(Card(Queen, Diamonds))
      )

      val expectedUpdatedPlayers = List(
        playerWithCards2,
        expectedUpdatedPlayer1)

      val expectedUpdatedGame = Game(
        MatchSuitAndValue,
        expectedUpdatedPlayers,
        Card(Ace, Spades) +: stack)

      result.unsafeRunSync() shouldBe GameInProgress(
        expectedUpdatedGame,
        playerWithCards2.playerId
      )
    }

    "when there is a match and matching player wins the round, give stack to matching player"in {
      val game = Game(
        MatchSuitAndValue,
        List(
          playerWithCards2,
          playerWithMatch
        ),
        stack
      )

      when(mockRandomNumberService.randomIntFrom0To100())
        .thenReturn(20)
        .thenReturn(10)

      val result = gameService.play(GameInProgress(game, playerWithMatch.playerId))

      val expectedUpdatedPlayerWithMatch = Player(
        playerWithMatch.playerId,
        List(Card(Ace, Spades), Card(Queen, Clubs), Card(Queen, Clubs))
      )

      val expectedUpdatedPlayers = List(
        playerWithCards2,
        expectedUpdatedPlayerWithMatch)

      val expectedUpdatedGame = Game(
        MatchSuitAndValue,
        expectedUpdatedPlayers,
        List.empty)

      result.unsafeRunSync() shouldBe GameInProgress(
        expectedUpdatedGame,
        playerWithCards2.playerId
      )
    }

    "when there is a match and matching player wins the round, give stack to non-matching player"in {
      val game = Game(
        MatchSuitAndValue,
        List(
          playerWithCards2,
          playerWithMatch
        ),
        stack
      )

      when(mockRandomNumberService.randomIntFrom0To100())
        .thenReturn(10)
        .thenReturn(20)


      val result = gameService.play(GameInProgress(game, playerWithMatch.playerId))

      val expectedUpdatedPlayerWithCards2 = Player(
        playerWithCards2.playerId,
        List(Card(Ace, Hearts), Card(Queen, Clubs), Card(Queen, Clubs), Card(Queen, Clubs))
      )

      val expectedUpdatedPlayerWithMatch = Player(
        playerWithMatch.playerId,
        List(Card(Ace, Spades))
      )

      val expectedUpdatedPlayers = List(
        expectedUpdatedPlayerWithCards2,
        expectedUpdatedPlayerWithMatch)

      val expectedUpdatedGame = Game(
        MatchSuitAndValue,
        expectedUpdatedPlayers,
        List.empty)

      result.unsafeRunSync() shouldBe GameInProgress(
        expectedUpdatedGame,
        playerWithCards2.playerId
      )
    }
  }
}
