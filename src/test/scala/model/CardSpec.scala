package model

import de.htwg.se.blackjack.model.{Card, Rank, Suit}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec with Matchers {
  "Card" should {
    "be Two of Diamonds" in {
      Card(Rank.Two, Suit.Diamonds).toString should be(
        s"${Rank.Two.toString} of ${Suit.Diamonds}, Value: 02"
      )
    }

    "correctly return string representations" in {
      Rank.Two.toString shouldEqual "2️⃣"
      Rank.Three.toString shouldEqual "3️⃣"
      Rank.Four.toString shouldEqual "4️⃣"
      Rank.Five.toString shouldEqual "5️⃣"
      Rank.Six.toString shouldEqual "6️⃣"
      Rank.Seven.toString shouldEqual "7️⃣"
      Rank.Eight.toString shouldEqual "8️⃣"
      Rank.Nine.toString shouldEqual "9️⃣"
      Rank.Ten.toString shouldEqual "🔟"
      Rank.Jack.toString shouldEqual "🃏"
      Rank.Queen.toString shouldEqual "👸"
      Rank.King.toString shouldEqual "🤴"
      Rank.Ace.toString shouldEqual "🅰️"
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
      anotherCard.toString shouldEqual s"${Rank.Queen} of ${Suit.Hearts}, Value: 10"
    }
  }
}
