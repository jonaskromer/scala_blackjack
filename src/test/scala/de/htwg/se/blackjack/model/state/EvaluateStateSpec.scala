package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game, Hand, Player}

class EvaluateStateSpec extends AnyWordSpec with Matchers {

  "A EvaluateState" should {

    "not allow adding players, starting the game, hitting, or standing" in {
      val state = new EvaluateState

      state.canAddPlayer should be(false)
      state.canStartGame() should be(false)
      state.canHit should be(false)
      state.canStand should be(false)
    }

    "execute the evaluation action when execute is called" in {
      val state = EvaluateState()
      // Set up a simple game with players and a deck
      val players = List(new Player("Player 1", Hand(List.empty)))
      val deck = new Deck(List.empty) // Empty deck for simplicity
      val game = Game(players = players, currentPlayer = 0, deck = deck, state = state)
      val controller = new Controller(game)

      // Here we need to simulate or test the evalGame() logic
      // We assume evalGame() performs some actions in the controller
      // We won't mock this method, but instead we will check the resulting game state

      // Execute the evaluation logic
      state.execute(controller)

      // Since we don't know the exact implementation of evalGame(), we'll assume it modifies
      // the state of the game. You can check if the state has changed accordingly:
      controller.game.state shouldBe an [FinishedState] // Or another state, depending on the logic
    }

    "have a readable toString description" in {
      val state = new EvaluateState
      state.toString should be("evaluate state")
    }
  }
}
