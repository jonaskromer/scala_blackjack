package view

import org.scalatest.wordspec.AnyWordSpec
import model.*
import model.GameState._
import org.scalatest.matchers.should.Matchers._

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

class TUISpec extends AnyWordSpec {

  "The TUI" should {

    "display a welcome message when the game starts" in {
      val game = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), INIT)
      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        println("Welcome to Scala Blackjack!")
        game.startGame() // Assuming startGame() might include some prints in the TUI
      }

      val printedOutput = outputCapture.toString
      printedOutput should include ("Welcome to Scala Blackjack!")
    }

    "display the player's action options after a card is drawn" in {
      val deck = Deck(List(Card(Rank.Five, Suit.Hearts), Card(Rank.Seven, Suit.Diamonds)))
      val player = Player("Alice", Hand(List.empty))
      val game = Game(List(player), 0, deck, DISTRIBUTE)
      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        val gameAfterHit = game.hit() // Assuming the TUI invokes hit() and shows output
        println("Choose your action: (h)it or (s)tand")
      }

      val printedOutput = outputCapture.toString
      printedOutput should include ("Alice drew a Five")
      printedOutput should include ("Remaining Cards:")
      printedOutput should include ("Choose your action: (h)it or (s)tand")
    }

    "display an overbuy message when a player exceeds 21 points" in {
      val overbuyingPlayer = Player("Alice", Hand(List(Card(Rank.Ten, Suit.Spades), Card(Rank.Jack, Suit.Hearts), Card(Rank.Three, Suit.Diamonds))))
      val otherPlayer = Player("Bob", Hand(List.empty))
      val deck = Deck(List(Card(Rank.Two, Suit.Clubs)))
      val game = Game(List(overbuyingPlayer, otherPlayer), 0, deck, DISTRIBUTE)
      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        game.evalOverbuy(overbuyingPlayer)
      }

      val printedOutput = outputCapture.toString
      printedOutput should include ("Alice overbought!")
      printedOutput should include ("--------------------------------------")
    }

    "prompt the dealer to take an action after players have finished" in {
      val deck = Deck(List(Card(Rank.Four, Suit.Spades), Card(Rank.Six, Suit.Diamonds), Card(Rank.Seven, Suit.Clubs)))
      val players = List(Player("Alice", Hand(List(Card(Rank.Five, Suit.Diamonds)))), Player("Bob", Hand(List(Card(Rank.Three, Suit.Hearts)))))
      val game = Game(players, 1, deck, DISTRIBUTE)
      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        game.stand() // Stand should trigger dealer's turn eventually
        println("Dealer's turn")
      }

      val printedOutput = outputCapture.toString
      printedOutput should include ("Dealer's turn")
    }

    "print the final results and winner(s) at the end of the game" in {
      val dealer = Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Spades))))
      val player = Player("Alice", Hand(List(Card(Rank.Seven, Suit.Diamonds), Card(Rank.Three, Suit.Clubs))))
      val game = Game(List(dealer, player), 0, Deck(List.empty).createShuffledDeck(), EVALUATE)
      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        game.evalGame()
      }

      val printedOutput = outputCapture.toString
      printedOutput should include ("Results:")
      printedOutput should include ("Alice")
      printedOutput should include ("Winners:")
    }
  }
}
