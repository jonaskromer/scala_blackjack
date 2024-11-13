package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Card, Deck, Game, Hand, Player, Rank, Suit}
import de.htwg.se.blackjack.model.*
import de.htwg.se.blackjack.model.state.{DealerState, DistributeState, EvaluateState, FinishedState, InitState}
import de.htwg.se.blackjack.view.Tui
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayOutputStream, PrintStream}

class GameSpec extends AnyWordSpec {

  "A Game" should {

    "print an overbuy message when a player exceeds 21 points" in {
      val overbuyingPlayer = Player("Alice", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Jack, Suit.Hearts), Card(Rank.Three, Suit.Spades))))
      val otherPlayer = Player("Bob", Hand(List(Card(Rank.Two, Suit.Diamonds))))
      val deck = Deck(List(Card(Rank.Four, Suit.Clubs)))
      val game = Game(List(overbuyingPlayer, otherPlayer), 0, deck, DistributeState())

      val outputCapture = new ByteArrayOutputStream()
      Console.withOut(new PrintStream(outputCapture)) {
        game.evalOverbuy(overbuyingPlayer)
      }

      val printedOutput = outputCapture.toString
      printedOutput should include("Alice overbought!")
      printedOutput should include("--------------------------------------")
    }

    "allow the current player to hit and draw a card" in {
      val initialDeck = Deck(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Hearts)))
      val player = Player("Alice", Hand(List.empty))
      val initialGame = Game(List(player), 0, initialDeck, DistributeState())
      val gameAfterHit = initialGame.hit()

      gameAfterHit.players.head.hand.cards should not be empty
    }

    "start in the INIT state with only the dealer" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), InitState())

      initialGame.state shouldBe InitState()
    }

    "add a player correctly" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), InitState())
      val gameWithPlayer = initialGame.addPlayer("Alice")

      gameWithPlayer.players.length shouldBe 2
    }

    "transition to DISTRIBUTE state when the game starts" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), InitState())
      val startedGame = initialGame.startGame()

      startedGame.state shouldBe DistributeState()
    }

    "transition to DEALER when all players finished" in {
      var game = Game(List(Player("DEALER", Hand(List.empty)),Player("Bob", Hand(List.empty))), 1, Deck(List.empty).createShuffledDeck(), InitState())
      game = game.stand()

      game.currentPlayer shouldBe 0
    }

    "allow the current player to draw a card" in {
      val deck = Deck(List(Card(Rank.Five, Suit.Hearts), Card(Rank.Seven, Suit.Clubs)))
      val initialGame = Game(List(Player("Alice", Hand(List.empty)), Player("Bob", Hand(List.empty))), 0, deck, DistributeState())
      val gameAfterDraw = initialGame.drawCard(initialGame.players.head, deck)

      gameAfterDraw.deck.cards.length shouldBe 1
    }

    "move to the next player after stand" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty)), Player("Alice", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), DistributeState())
      val gameAfterStand = initialGame.stand()

      gameAfterStand.currentPlayer shouldBe 1
    }

    "switch to DEALER state when the dealer’s turn begins" in {
      val gameWithDealerTurn = Game(List(Player("DEALER", Hand(List.empty)), Player("Alice", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), DistributeState()).evalIfDealer()

      gameWithDealerTurn.state shouldBe DealerState()
    }

    "handle dealer drawing cards until total value is 17 or higher" in {
      val deck = Deck(List(Card(Rank.Five, Suit.Hearts), Card(Rank.Six, Suit.Diamonds), Card(Rank.Seven, Suit.Spades)))
      val dealer = Player("DEALER", Hand(List.empty))
      val initialGame = Game(List(dealer), 0, deck, DealerState())
      val gameAfterDealerDraw = initialGame.dealerDraw()

      gameAfterDealerDraw.players.head.hand.totalValue should be >= 17
      gameAfterDealerDraw.state shouldBe EvaluateState()
    }

    "evaluate winners of the game and print results" in {
      val dealer = Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Spades))))
      val player = Player("Alice", Hand(List(Card(Rank.Two, Suit.Diamonds), Card(Rank.Three, Suit.Hearts))))
      val gameToEvaluate = Game(List(dealer, player), 0, Deck(List.empty).createShuffledDeck(), EvaluateState())
      val evaluatedGame = gameToEvaluate.evalGame()

      evaluatedGame.state shouldBe FinishedState()
      evaluatedGame.players(1).hand.totalValue shouldBe 5
    }

    "evaluate loosers of the game and print results" in {
      val dealer = Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Spades), Card(Rank.Ten, Suit.Hearts))))
      val player = Player("Alice", Hand(List(Card(Rank.Ten, Suit.Diamonds), Card(Rank.Ten, Suit.Hearts), Card(Rank.Ten, Suit.Hearts))))
      val gameToEvaluate = Game(List(dealer, player), 0, Deck(List.empty).createShuffledDeck(), EvaluateState())
      val evaluatedGame = gameToEvaluate.evalGame()

      evaluatedGame.state shouldBe FinishedState()
    }

    "restart the game with only the dealer and a new shuffled deck" in {
      val initialGame = Game(List(Player("Alice", Hand(List.empty)), Player("Bob", Hand(List.empty))), 1, Deck(List.empty).createShuffledDeck(), FinishedState())
      val restartedGame = initialGame.setup()

      restartedGame.state shouldBe InitState()
      restartedGame.deck.cards.length shouldBe 52
    }

    "should have correct string representation" in {
      val game = Game(List.empty, 0, Deck(List.empty).createShuffledDeck(), DistributeState())

      todo
    }
  }
}
