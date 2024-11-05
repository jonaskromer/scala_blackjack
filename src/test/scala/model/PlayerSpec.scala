import org.scalatest.wordspec.AnyWordSpec
import model.{Player, Hand, Card, Rank, Suit}
import view.ConsoleColors

class PlayerSpec extends AnyWordSpec {

  "A Player" should {

    "print the player's name and hand correctly" in {

      val hand = Hand(List(Card(Rank.Two, Suit.Hearts), Card(Rank.Three, Suit.Spades)))
      val player = Player("Alice", hand)
      
      val expectedOutput = s"Alice: ${hand.toString}"
      assert(player.printHand == expectedOutput)
    }

    "handle an empty hand correctly" in {
      val emptyHand = Hand(List.empty)
      val player = Player("Bob", emptyHand)

      val expectedOutput = s"Bob: ${emptyHand.toString}"
      assert(player.printHand == expectedOutput)
    }
  }
}
