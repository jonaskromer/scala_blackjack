import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.model._
import de.htwg.se.blackjack.model.state._

class GameSpec extends AnyWordSpec with Matchers {

  "A Game" should {

    "add a player" in {
      val game = Game(players = List(Player("Player 1", Hand(List.empty))), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = InitState())
      val newGame = game.addPlayer("Player 2")

      newGame.players.size shouldBe 2
      newGame.players.map(_.name) should contain allOf ("Player 1", "Player 2")
    }

    "start the game and transition to DistributeState" in {
      val game = Game(players = List(Player("Player 1", Hand(List.empty))), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = InitState())
      val newGame = game.startGame()

      newGame.state shouldBe a [DistributeState]
    }

    "draw a card for the player and update their hand" in {
      val deck = Deck(List(Card(Rank.Ace, Suit.Hearts), Card(Rank.Seven, Suit.Clubs)))
      val game = Game(players = List(Player("Player 1", Hand(List.empty))), currentPlayer = 0, deck = deck.createShuffledDeck(), state = InitState())
      val newGame = game.drawCard(game.players.head, game.deck)

      newGame.players.head.hand.cards.size shouldBe 1
    }

    "execute a hit action and update the game state" in {
      val deck = Deck(List(Card(Rank.Ace, Suit.Hearts), Card(Rank.Seven, Suit.Clubs)))
      val game = Game(players = List(Player("Player 1", Hand(List.empty))), currentPlayer = 0, deck = deck.createShuffledDeck(), state = InitState())
      val newGame = game.hit()

      newGame.players.head.hand.cards.size shouldBe 1
      newGame.state shouldBe a [DealerState]  // Assuming the hit method triggers dealer state change if needed
    }

    "execute a stand action and transition to next player or dealer" in {
      val game = Game(players = List(
        Player("Player 1", Hand(List(Card(Rank.Seven, Suit.Hearts), Card(Rank.Three, Suit.Spades)))),
        Player("Player 2", Hand(List.empty))
      ), currentPlayer = 1, deck = Deck(List.empty).createShuffledDeck(), state = PlayerState())
      val newGame = game.stand()

      newGame.currentPlayer shouldBe 0
      newGame.state shouldBe a [DealerState]
    }

    "handle the dealer's draw and transition to EvaluateState" in {
      val player = Player("Player 1", Hand(List(Card(Rank.Seven, Suit.Hearts), Card(Rank.Three, Suit.Spades))))  // Total value 10
      val deck = Deck(List(Card(Rank.Eight, Suit.Clubs), Card(Rank.Six, Suit.Spades)))
      val game = Game(players = List(player), currentPlayer = 0, deck = deck.createShuffledDeck(), state = DealerState())
      val newGame = game.dealerDraw()

      newGame.state shouldBe a [EvaluateState]
    }

    "reset the game with setup" in {
      val game = Game(players = List(Player("Player 1", Hand(List.empty))), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = InitState())
      val newGame = game.setup()

      newGame.players.size shouldBe 1
      newGame.players.head.name shouldBe "DEALER"
      newGame.state shouldBe a [PreInitState]
    }

    "game should print correct" in {
      val game = Game(players = List(Player("Player 1", Hand(List.empty))), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = InitState())
      game.toString() should include("-----------------------------------------------------------------------------------------------------------")
      game.toString() should include("Player 1")
      game.toString() should include("Empty Hand")
      game.toString() should include("-----------------------------------------------------------------------------------------------------------")
    }

    "evaldealer should return current player when not dealers turn" in {
      val game = Game(players = List(Player("Dealer", Hand(List.empty)), Player("Player 1", Hand(List.empty)), Player("Player 2", Hand(List.empty))), currentPlayer = 1, deck = Deck(List.empty).createShuffledDeck(), state = PlayerState())
      val newGame = game.evalIfDealer()
      newGame.currentPlayer shouldBe 1
    }
  }
}
