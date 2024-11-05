import model.GameState._
import model._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class GameSpec extends AnyWordSpec {

  "A Game" should {

    "start in the INIT state with only the dealer" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), INIT)

      initialGame.players.map(_.name) should contain("DEALER")
      initialGame.players.length shouldBe 1
      initialGame.state shouldBe INIT
    }

    "add a player correctly" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), INIT)
      val gameWithPlayer = initialGame.addPlayer("Alice")

      gameWithPlayer.players.map(_.name) should contain("Alice")
      gameWithPlayer.players.length shouldBe 2
    }

    "transition to DISTRIBUTE state when the game starts" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), INIT)
      val startedGame = initialGame.startGame()

      startedGame.state shouldBe DISTRIBUTE
    }

    "allow the current player to draw a card" in {
      val deck = Deck(List(Card(Rank.Five, Suit.Hearts), Card(Rank.Seven, Suit.Clubs)))
      val initialGame = Game(List(Player("Alice", Hand(List.empty))), 0, deck, DISTRIBUTE)
      val gameAfterDraw = initialGame.drawCard(initialGame.players.head, deck)

      gameAfterDraw.players.head.hand.cards.map(_.rank) should contain(Rank.Five)
      gameAfterDraw.deck.cards.length shouldBe 1
    }

    "move to the next player after stand" in {
      val initialGame = Game(List(Player("DEALER", Hand(List.empty)), Player("Alice", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), DISTRIBUTE)
      val gameAfterStand = initialGame.stand()

      gameAfterStand.currentPlayer shouldBe 1
    }

    "switch to DEALER state when the dealer’s turn begins" in {
      val gameWithDealerTurn = Game(List(Player("DEALER", Hand(List.empty)), Player("Alice", Hand(List.empty))), 0, Deck(List.empty).createShuffledDeck(), DISTRIBUTE).evalIfDealer()

      gameWithDealerTurn.state shouldBe DEALER
    }

    "handle dealer drawing cards until total value is 17 or higher" in {
      val deck = Deck(List(Card(Rank.Five, Suit.Hearts), Card(Rank.Six, Suit.Diamonds), Card(Rank.Seven, Suit.Spades)))
      val dealer = Player("DEALER", Hand(List.empty))
      val initialGame = Game(List(dealer), 0, deck, DEALER)
      val gameAfterDealerDraw = initialGame.dealerDraw()

      gameAfterDealerDraw.players.head.hand.totalValue should be >= 17
      gameAfterDealerDraw.state shouldBe EVALUATE
    }

    "evaluate the game and print results" in {
      val dealer = Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Clubs), Card(Rank.Six, Suit.Spades))))
      val player = Player("Alice", Hand(List(Card(Rank.Two, Suit.Diamonds), Card(Rank.Three, Suit.Hearts))))
      val gameToEvaluate = Game(List(dealer, player), 0, Deck(List.empty).createShuffledDeck(), EVALUATE)
      val evaluatedGame = gameToEvaluate.evalGame()

      evaluatedGame.state shouldBe FINISHED
      evaluatedGame.players.head.hand.totalValue shouldBe 16 // Dealer
      evaluatedGame.players(1).hand.totalValue shouldBe 5    // Player
    }

    "restart the game with only the dealer and a new shuffled deck" in {
      val initialGame = Game(List(Player("Alice", Hand(List.empty)), Player("Bob", Hand(List.empty))), 1, Deck(List.empty).createShuffledDeck(), FINISHED)
      val restartedGame = initialGame.restart()

      restartedGame.players.map(_.name) should contain("DEALER")
      restartedGame.players.length shouldBe 1
      restartedGame.state shouldBe INIT
      restartedGame.deck.cards.length shouldBe 52 // Assuming a standard deck size is restored
    }
  }
}
