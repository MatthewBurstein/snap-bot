package snapbot.domain

import snapbot.domain.CardValues.{Ace, Five}
import snapbot.domain.Suits.{Clubs, Diamonds, Hearts}
import snapbot.utils.UnitTest

class GameTest extends UnitTest {
  "isMatch" should {
    "match suit in MatchSuit" in {
      val game = Game(MatchSuit, List.empty, List(Card(Ace, Clubs), Card(Five, Clubs)))

      game.isMatch shouldBe true
    }

    "not match value in MatchSuit" in {
      val game = Game(MatchSuit, List.empty, List(Card(Ace, Diamonds), Card(Ace, Clubs)))

      game.isMatch shouldBe false
    }

    "match value in MatchValue" in {
      val game = Game(MatchValue, List.empty, List(Card(Ace, Diamonds), Card(Ace, Clubs)))

      game.isMatch shouldBe true
    }

    "not match suit in MatchValue" in {
      val game = Game(MatchValue, List.empty, List(Card(Five, Diamonds), Card(Ace, Diamonds)))

      game.isMatch shouldBe false
    }

    "match suit and value in MatchSuitAndValue" in {
      val game = Game(MatchSuitAndValue, List.empty, List(Card(Five, Diamonds), Card(Five, Diamonds)))

      game.isMatch shouldBe true
    }

    "not match suit only in MatchSuitAndValue" in {
      val game = Game(MatchSuitAndValue, List.empty, List(Card(Ace, Diamonds), Card(Five, Diamonds)))

      game.isMatch shouldBe false
    }

    "not match value only in MatchSuitAndValue" in {
      val game = Game(MatchSuitAndValue, List.empty, List(Card(Ace, Clubs), Card(Ace, Diamonds)))

      game.isMatch shouldBe true
    }
  }

}
