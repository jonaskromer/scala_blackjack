import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class CardSpec extends AnyWordSpec with Matchers {
  "Card" should {
    "be Two of Diamonds" in {
      Card(Rank.Two, Suit.Diamonds).toString should be (
        s"Two of Diamonds, Value: 2"
      )
    }
  }
}
