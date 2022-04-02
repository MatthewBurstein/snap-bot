package snapbot.service

import snapbot.domain.Card

import scala.util.Random

class ShufflerService {
  def shuffle(cards: List[Card]): List[Card] = Random.shuffle(cards)
}
