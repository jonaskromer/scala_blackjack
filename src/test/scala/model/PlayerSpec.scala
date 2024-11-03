import org.scalatest.wordspec.AnyWordSpec
import model.{Player, Hand, Card, Rank, Suit}
import view.ConsoleColors

class PlayerSpec extends AnyWordSpec {

  "A Player" should {

    "print the player's name and hand correctly" in {
      // Prepare a hand with some cards
      val hand = Hand(List(Card(Rank.Two, Suit.Hearts), Card(Rank.Three, Suit.Spades)))
      val player = Player("Alice", hand)

      // Expected format for printHand, assuming Hand.toString formats as "Total Value: 05 | Two of Hearts Three of Spades"
      val expectedOutput = s"Alice: ${hand.toString}"
      assert(player.printHand == expectedOutput)
    }

    "handle an empty hand correctly" in {
      val emptyHand = Hand(List.empty)
      val player = Player("Bob", emptyHand)

      // Expected format with an empty hand, assuming Hand.toString displays something reasonable for an empty list
      val expectedOutput = s"Bob: ${emptyHand.toString}"
      assert(player.printHand == expectedOutput)
    }
  }
}
