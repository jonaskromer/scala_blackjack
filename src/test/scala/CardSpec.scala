import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec with Matchers {
  "Card" should {
    "be Two of Diamonds" in {
      Card(Rank.Two, Suit.Diamonds).toString should be(
        s"Two of Diamonds, Value: 2"
      )
    }
    "have a rank and a suit" in {
      val card = Card(Rank.Two, Suit.Hearts)
      card.rank shouldEqual Rank.Two
      card.suit shouldEqual Suit.Hearts
    }

    "return the correct value for the rank" in {
      val aceCard = Card(Rank.Ace, Suit.Clubs)
      aceCard.rank.value shouldEqual 11
    }

    "have a correct string representation" in {
      val anotherCard = Card(Rank.Queen, Suit.Hearts)
      anotherCard.toString shouldEqual "Queen of Hearts, Value: 10"
    }
  }
}
