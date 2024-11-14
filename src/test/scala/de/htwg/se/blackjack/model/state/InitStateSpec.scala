package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game}

class InitStateSpec extends AnyWordSpec with Matchers {

  "An InitState" should {

    "allow adding players and starting the game, but not hitting or standing" in {
      val state = InitState()

      state.canAddPlayer should be(true)
      state.canStartGame() should be(true)
      state.canHit should be(false)
      state.canStand should be(false)
    }

    "perform no actions when execute is called" in {
      val state = InitState()
      // Set up a game and controller instance with InitState
      val game = Game(players = List.empty, currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = state)
      val controller = new Controller(game)

      // Call execute on InitState and verify no state changes occur
      state.execute(controller)

      // Check that the state remains as InitState after execute is called
      controller.game.state shouldBe an [InitState]
    }

    "have a readable toString description" in {
      val state = InitState()
      state.toString should be("initial state")
    }
  }
}
