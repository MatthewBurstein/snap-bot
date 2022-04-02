package snapbot.domain

case class Card(value: CardValues.Value, suit: Suits.Value)

object Card {
  def createDeck: List[Card] = for {
    suit <- Suits.all
    value <- CardValues.all
  } yield Card(value, suit)
}

object Suits extends Enumeration {
  val Hearts, Clubs, Spades, Diamonds = Value

  val all: List[Suits.Value] = values.toList
}

object CardValues extends Enumeration {
  val Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King = Value

  val all: List[CardValues.Value] = values.toList
}



