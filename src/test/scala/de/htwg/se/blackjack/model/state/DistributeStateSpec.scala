package de.htwg.se.blackjack.model.state

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game, Hand, Player}

class DistributeStateSpec extends AnyWordSpec with Matchers {

  "A DistributeState" should {

    "not allow adding players, starting the game, hitting, or standing" in {
      val state = new DistributeState

      state.canAddPlayer should be(false)
      state.canStartGame() should be(false)
      state.canHit should be(false)
      state.canStand should be(false)
    }

    "transition to PlayerState when execute is called" in {
      val state = DistributeState()
      val players = List(Player("Player 1", Hand(List.empty)), Player("Player 2", Hand(List.empty)))
      val game = Game(players = players, currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = state)
      val controller = new Controller(game)

      state.execute(controller)

      controller.game.state shouldBe an [PlayerState]
      controller.game.currentPlayer should be(1)
    }

    "have a readable toString description" in {
      val state = new DistributeState
      state.toString should be("distribute state")
    }
  }
}
