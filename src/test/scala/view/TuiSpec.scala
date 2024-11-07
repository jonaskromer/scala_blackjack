package view

import org.scalatest.wordspec.AnyWordSpec
import model.*
import model.GameState._
import org.scalatest.matchers.should.Matchers._

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

class TUISpec extends AnyWordSpec {

  "The TUI" should {

    "change state when game starts" in {
      var game = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), INIT)

      val tui = Tui()
      game = tui.processInput("start", game)

      game.state shouldBe DISTRIBUTE
    }

    "print help" in {
      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        var game = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), INIT)
        val tui = Tui()
        game = tui.processInput("help", game)
      }

      val printedOutput = outputCapture.toString
      printedOutput should include("TODO")
    }

    "return an emtpy game" in {
      val deck = Deck(List(Card(Rank.Five, Suit.Hearts), Card(Rank.Seven, Suit.Diamonds)))
      val player = Player("Alice", Hand(List.empty))
      var game = Game(List(player), 0, deck, DISTRIBUTE)
      val tui = Tui()

      game = tui.processInput("restart", game)

      game.state shouldBe INIT

    }

    "should add card to player hand" in {
      val player = Player("Alice", Hand(List().empty))
      val deck = Deck(List(Card(Rank.Two, Suit.Clubs)))
      var game = Game(List(player), 0, deck, PLAYER)
      val tui = Tui()

      game = tui.processInput("hit", game)

      game.players.head.hand.cards.size shouldBe 1
  }

    "should go to next player" in {
      val deck = Deck(List(Card(Rank.Four, Suit.Spades), Card(Rank.Six, Suit.Diamonds), Card(Rank.Seven, Suit.Clubs)))
      val players = List(Player("Alice", Hand(List(Card(Rank.Five, Suit.Diamonds)))), Player("Bob", Hand(List(Card(Rank.Three, Suit.Hearts)))))
      var game = Game(players, 0, deck, PLAYER)
      val tui = Tui()

      game = tui.processInput("stand", game)

      game.currentPlayer shouldBe 1
    }

    "print exit and change state" in {
      val dealer = Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Spades))))
      val player = Player("Alice", Hand(List(Card(Rank.Seven, Suit.Diamonds), Card(Rank.Three, Suit.Clubs))))
      var game = Game(List(dealer, player), 0, Deck(List.empty).createShuffledDeck(), EVALUATE)
      val tui = Tui()


      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        game = tui.processInput("exit", game)
      }

      val printedOutput = outputCapture.toString
      printedOutput should include ("Exiting game...")
      game.state shouldBe EXIT
    }

    "nothing happens" in {
      val dealer = Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Spades))))
      val player = Player("Alice", Hand(List(Card(Rank.Seven, Suit.Diamonds), Card(Rank.Three, Suit.Clubs))))
      val game = Game(List(dealer, player), 0, Deck(List.empty).createShuffledDeck(), EVALUATE)
      val tui = Tui()


      val newGame = tui.processInput("", game)

      newGame shouldBe game
    }

    "print hands" in {
      val player = Player("Alice", Hand(List(Card(Rank.Seven, Suit.Diamonds), Card(Rank.Three, Suit.Clubs))))
      var game = Game(List(player), 0, Deck(List.empty).createShuffledDeck(), EVALUATE)
      val tui = Tui()


      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        game = tui.processInput("printHands", game)
      }

      val printedOutput = outputCapture.toString
      printedOutput should include(s"${player.name}: ${player.hand}")
    }

    "add player" in {
      val player = Player("Alice", Hand(List(Card(Rank.Seven, Suit.Diamonds), Card(Rank.Three, Suit.Clubs))))
      var game = Game(List(player), 0, Deck(List.empty).createShuffledDeck(), EVALUATE)
      val tui = Tui()


      val outputCapture = new ByteArrayOutputStream()

      Console.withOut(new PrintStream(outputCapture)) {
        game = tui.processInput("add NEWPLAYER", game)
      }

      val printedOutput = outputCapture.toString
      printedOutput should include("Added player NEWPLAYER")
      game.players.length shouldBe 2
    }
  }
}
