import org.scalatest.wordspec.AnyWordSpec
import model.{Hand, Card, Rank, Suit}
import view.ConsoleColors

class HandSpec extends AnyWordSpec {

  "A Hand" should {

    "correctly add a card to the hand" in {
      val hand = Hand(List(Card(Rank.Two, Suit.Hearts)))
      val updatedHand = hand.addCard(Card(Rank.Three, Suit.Spades))
      assert(updatedHand.cards.size == 2)
      assert(updatedHand.cards.contains(Card(Rank.Two, Suit.Hearts)))
      assert(updatedHand.cards.contains(Card(Rank.Three, Suit.Spades)))
    }

    "calculate total value without aces correctly" in {
      val hand = Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Seven, Suit.Diamonds)))
      assert(hand.totalValue == 17)
    }

    "calculate total value with one ace correctly" in {
      val hand = Hand(List(Card(Rank.Ace, Suit.Clubs), Card(Rank.Six, Suit.Hearts)))
      assert(hand.totalValue == 17) // Ace should count as 11 here
    }

//    "adjust ace value when total exceeds 21" in {
//      val hand = Hand(List(Card(Rank.Ace, Suit.Clubs), Card(Rank.King, Suit.Spades), Card(Rank.Queen, Suit.Diamonds)))
//      assert(hand.totalValue == 21) // Ace should count as 1 here
//    }

    "not adjust ace value if total is 21 or below" in {
      val hand = Hand(List(Card(Rank.Ace, Suit.Clubs), Card(Rank.Nine, Suit.Spades)))
      assert(hand.totalValue == 20) // Ace should count as 11
    }

    "display total value formatted with two digits" in {
      val hand = Hand(List(Card(Rank.Two, Suit.Clubs), Card(Rank.Three, Suit.Diamonds)))
      val expectedOutput = ConsoleColors.colorize("Total Value: 05 | Two of Clubs, Value: 2, Three of Diamonds, Value: 3\n", ConsoleColors.BRIGHT_GREEN)
      assert(hand.toString == expectedOutput)
    }
  }
}
