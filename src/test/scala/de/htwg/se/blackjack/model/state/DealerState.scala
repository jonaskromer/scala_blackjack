package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game, Hand, Player}

class DealerStateSpec extends AnyWordSpec with Matchers {

  "A DealerState" should {

    "not allow adding players, starting the game, hitting, or standing" in {
      val state = new DealerState()

      state.canAddPlayer should be(false)
      state.canStartGame() should be(false)
      state.canHit should be(false)
      state.canStand should be(false)
    }

    "execute the dealer's draw action when execute is called" in {
      // Prepare a simple deck and players for testing
      val state = DealerState()
      val players = List(new Player("Player 1", Hand(List.empty)))
      val deck = Deck(List.empty).createShuffledDeck() // Create a simple deck (could be mocked for simplicity)
      val game = Game(players = players, currentPlayer = 0, deck = deck, state = state)
      val controller = new Controller(game)

      // Execute the dealer's action (this should affect the game state)
      state.execute(controller)

      // Verify the game state changes or that some action was performed
      // Assuming `dealerDraw` would update the deck or game state, you can check that here
      // This example assumes that `deck` would change or that some logic inside `dealerDraw` happens.
      // You can test the game state change if needed:
      controller.game.state shouldBe an [EvaluateState]  // Or another state based on your logic
    }

    "have a readable toString description" in {
      val state = new DealerState
      state.toString should be("dealer state")
    }
  }
}
