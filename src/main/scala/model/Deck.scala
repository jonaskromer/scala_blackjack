package model

import scala.util.Random


case class Deck(cards: List[Card]):

  def createShuffledDeck(seed: Option[Long] = None): Deck =
    val allCards = for
      suit <- Suit.values.toList
      rank <- Rank.values.toList
    yield Card(rank, suit)
    
    val random = seed match
      case Some(s) => new Random(s)
      case None    => new Random()

    Deck(random.shuffle(allCards))
  
  def draw(): (Option[Card], Deck) =
    cards match
      case Nil =>
        println("No cards left!")
        (None, this)
      case head :: tail =>
        (Some(head), Deck(tail))
