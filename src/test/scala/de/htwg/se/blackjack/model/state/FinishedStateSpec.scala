package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game, Hand, Player}

class FinishedStateSpec extends AnyWordSpec with Matchers {

  "A FinishedState" should {

    "not allow adding players, starting the game, hitting, or standing" in {
      val state = new FinishedState

      state.canAddPlayer should be(false)
      state.canStartGame() should be(false)
      state.canHit should be(false)
      state.canStand should be(false)
    }

    "not change the game state when execute is called" in {
      val state = FinishedState()
      // Set up a simple game with players
      val players = List(new Player("Player 1", Hand(List.empty)))
      val game = Game(players = players, currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = state)
      val controller = new Controller(game)

      // Call execute on the FinishedState and check that the state doesn't change
      val initialState = game.state
      state.execute(controller)

      // The state should remain the same since FinishedState does not modify the game
      game.state should be(initialState)
    }

    "have a readable toString description" in {
      val state = new FinishedState
      state.toString should be("finished state")
    }
  }
}
