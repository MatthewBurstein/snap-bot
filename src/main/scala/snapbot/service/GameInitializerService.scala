package snapbot.service

import snapbot.domain.{Game, MatchType}

import scala.io.StdIn

class GameInitializerService {
  def initialize(): Game = {
    println("Let's play snap! How many decks should we use?")

    // TODO what if this is not an int
    val numberOfDecksStr = StdIn.readLine().toInt

    println("Should cards be matched on suits, values or both? (Please enter 's', 'v' or 'b')")

    // TODO what if this is not valid
    val matchTypeStr = StdIn.readLine()
    val matchType = MatchType.fromString(matchTypeStr)

    Game(numberOfDecksStr, matchType)
  }
}
