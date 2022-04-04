package snapbot.domain

import snapbot.utils.UnitTest

class MatchStrategyTest extends UnitTest {
  "fromString" should {
    "parse MatchSuit from s" in {
      MatchStrategy.fromString("s") shouldBe MatchSuit
    }
    "parse MatchValue from v" in {
      MatchStrategy.fromString("v") shouldBe MatchValue
    }
    "parse MatchBoth from b" in {
      MatchStrategy.fromString("b") shouldBe MatchSuitAndValue
    }

    "throw an error on unknown value" in {
      an[RuntimeException] shouldBe thrownBy(MatchStrategy.fromString("unknown"))
    }
  }
}
