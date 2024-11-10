package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.{Card, Deck, Rank, Suit}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec with Matchers {

  "A Deck" should {

    "create a shuffled deck with all cards" in {
      val deck = Deck(Nil).createShuffledDeck()
      
      val allCards = for {
        suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)
        rank <- Rank.values.toList
      } yield Card(rank, suit)

      allCards.forall(deck.cards.contains) shouldEqual true
    }

    "draw a card and reduce the deck size" in {
      val deck = Deck(Nil).createShuffledDeck()
      val originalSize = deck.cards.size

      val updatedDeck = deck.draw()

      updatedDeck._2.cards.size shouldEqual (originalSize - 1)
    }

    "not draw a card when the deck is empty" in {
      val emptyDeck = Deck(Nil)

      val updatedDeck = emptyDeck.draw()

      updatedDeck._2.cards.size shouldEqual 0
    }

    "produce the same shuffled deck with the same seed" in {
      val seed = 12345L
      val deck1 = Deck(Nil).createShuffledDeck(Some(seed))
      val deck2 = Deck(Nil).createShuffledDeck(Some(seed))

      deck1.cards shouldEqual deck2.cards
    }
  }
}
