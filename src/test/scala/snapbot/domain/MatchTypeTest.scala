package snapbot.domain

import snapbot.utils.UnitTest

class MatchTypeTest extends UnitTest {
  "fromString" should {
    "parse MatchSuit from s" in {
      MatchType.fromString("s") shouldBe MatchSuit
    }
    "parse MatchValue from v" in {
      MatchType.fromString("v") shouldBe MatchValue
    }
    "parse MatchBoth from b" in {
      MatchType.fromString("b") shouldBe MatchBoth
    }

    "throw an error on unknown value" in {
      an[RuntimeException] shouldBe thrownBy(MatchType.fromString("unknown"))
    }
  }
}
