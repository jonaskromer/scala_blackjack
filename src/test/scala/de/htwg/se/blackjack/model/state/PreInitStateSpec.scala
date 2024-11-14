package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.model.state.{GameState, InitState, PreInitState}
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.Game

class PreInitStateSpec extends AnyWordSpec with Matchers {

  "A PreInitState" should {

    "allow adding players but not starting the game, hitting, or standing" in {
      val state = PreInitState() 

      state.canAddPlayer should be(true)
      state.canStartGame() should be(false)
      state.canHit should be(false)
      state.canStand should be(false)
    }

    "transition to InitState when execute is called" in {
      val state = PreInitState()
      // Set up a game and controller instance with PreInitState
      val game = Game(players = List.empty, currentPlayer = 0, deck = null, state = state)
      val controller = new Controller(game)

      // Call execute on PreInitState and check that the game state changes to InitState
      state.execute(controller)

      controller.game.state shouldBe an [InitState]
    }

    "have a readable toString description" in {
      val state = new PreInitState
      state.toString should be(" pre initial state")
    }
  }
}
