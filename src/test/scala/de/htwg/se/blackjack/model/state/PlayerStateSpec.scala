package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Game, Hand, Player}

class PlayerStateSpec extends AnyWordSpec with Matchers {

  "A PlayerState" should {

    "allow hitting and standing, but not adding players or starting the game" in {
      val state = new PlayerState

      state.canAddPlayer should be(false)
      state.canStartGame() should be(false)
      state.canHit should be(true)
      state.canStand should be(true)
    }

    "transition to DealerState when execute is called for the first player" in {
      val state = PlayerState()
      val players = List(Player("Player 1", Hand(List.empty)))
      val game = Game(players = players, currentPlayer = 0, deck = null, state = state)
      val controller = new Controller(game)

      state.execute(controller)

      controller.game.state shouldBe an [DealerState]
      controller.game.currentPlayer should be(0) // The current player stays the same here, but it's up to your game logic
    }

    "stay in PlayerState for subsequent players" in {
      val state = PlayerState()

      val players = List(Player("Player 1", Hand(List.empty)), Player("Player 2", Hand(List.empty)))
      val game = Game(players = players, currentPlayer = 1, deck = null, state = state)
      val controller = new Controller(game)

      state.execute(controller)

      controller.game.state shouldBe an [PlayerState]
      controller.game.currentPlayer should be(1) // Still the second player
    }

    "have a readable toString description" in {
      val state = new PlayerState
      state.toString should be("player state")
    }
  }
}
