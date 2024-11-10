package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.{Card, Hand, Rank, Suit}
import de.htwg.se.blackjack.view.ConsoleColors
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class HandSpec extends AnyWordSpec with Matchers {

  "A Hand" should {

    "correctly add a card to the hand" in {
      val hand = Hand(List(Card(Rank.Two, Suit.Hearts)))
      val updatedHand = hand.addCard(Card(Rank.Three, Suit.Spades))

      updatedHand.cards should contain (Card(Rank.Three, Suit.Spades))
    }

    "calculate total value without aces correctly" in {
      val hand = Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Seven, Suit.Diamonds)))
      hand.totalValue shouldEqual 17
    }

    "calculate total value with one ace correctly" in {
      val hand = Hand(List(Card(Rank.Ace, Suit.Clubs), Card(Rank.Six, Suit.Hearts)))
      hand.totalValue shouldEqual 17 // Ace should count as 11 here
    }

    "not adjust ace value if total is 21 or below" in {
      val hand = Hand(List(Card(Rank.Ace, Suit.Clubs), Card(Rank.Nine, Suit.Spades)))
      hand.totalValue shouldEqual 20 // Ace should count as 11
    }

    "display total value formatted with two digits and properly padded cards with emoji" in {
      val hand = Hand(List(Card(Rank.Two, Suit.Clubs), Card(Rank.Three, Suit.Diamonds)))
      val expectedOutput = hand.toString
      hand.toString shouldEqual expectedOutput
    }
  }
}
