package model

import model.{Card, Deck, Rank, Suit}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec with Matchers {

  "A Deck" should {

    "create a shuffled deck with all cards" in {
      val deck = Deck(Nil).createShuffledDeck()

      // Es sollten 52 Karten im Deck sein (4 Farben * 13 Werte)
      deck.cards.size shouldEqual 52

      // Alle Karten sollten vorhanden sein
      val allCards = for {
        suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)
        rank <- Rank.values.toList
      } yield Card(rank, suit)

      allCards.forall(deck.cards.contains) shouldEqual true
    }

    "draw a card and reduce the deck size" in {
      val deck = Deck(Nil).createShuffledDeck()
      val originalSize = deck.cards.size

      // Ziehe eine Karte
      val updatedDeck = deck.draw()

      // Deckgröße sollte um 1 verringert sein
      updatedDeck._2.cards.size shouldEqual (originalSize - 1)
    }

    "not draw a card when the deck is empty" in {
      val emptyDeck = Deck(Nil)

      // Ziehe eine Karte
      val updatedDeck = emptyDeck.draw()

      // Die Größe des Decks sollte unverändert bleiben
      updatedDeck._2.cards.size shouldEqual 0
    }

    "produce the same shuffled deck with the same seed" in {
      val seed = 12345L
      val deck1 = Deck(Nil).createShuffledDeck(Some(seed))
      val deck2 = Deck(Nil).createShuffledDeck(Some(seed))

      // Ensure the two decks are identical
      deck1.cards shouldEqual deck2.cards
    }
  }
}
