package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.{Card, Hand, Player, Rank, Suit}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {

    "print the player's name and hand correctly" in {
      val hand = Hand(List(Card(Rank.Two, Suit.Hearts), Card(Rank.Three, Suit.Spades)))
      val player = Player("Alice", hand)

      val expectedOutput = s"Alice: ${hand.toString}"
      player.printHand should include(expectedOutput)
    }

    "handle an empty hand correctly" in {
      val emptyHand = Hand(List.empty)
      val player = Player("Bob", emptyHand)

      val expectedOutput = s"Bob: ${emptyHand.toString}"
      player.printHand should include(expectedOutput)
    }
  }
}
