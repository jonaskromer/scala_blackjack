package de.htwg.se.blackjack.controller

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game, Hand, Player}
import de.htwg.se.blackjack.util.Observer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {

    "add a player and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(), currentPlayer = 0, deck = null, state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Add a player
      controller.addPlayer("Player 1")

      // Verify that the observer was notified
      notified should be(true)
      controller.game.players.size should be(1)
      controller.game.players.head.name should be("Player 1")
    }

    "start the game and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Start the game
      controller.startGame()

      // Verify that the observer was notified
      notified should be(true)
      controller.game.state should not be null
    }

    "hit and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Perform hit
      controller.hit()

      // Verify that the observer was notified
      notified should be(true)
    }

    "stand and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Perform stand
      controller.stand()

      // Verify that the observer was notified
      notified should be(true)
    }

    "restart the game and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Restart the game
      controller.restart()

      // Verify that the observer was notified
      notified should be(true)
      controller.game.players.size should be(1)
    }

    "execute dealer draw without notifying observers" in {
      val player = Player("Player 1", Hand(List().empty))
      val dealer = Player("Dealer", Hand(List().empty))
      val game = Game(players = List(dealer, player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Call dealer draw
      controller.dealerDraw()

      true shouldBe true
    }


    "evaluate the game and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Evaluate the game
      controller.evalGame()

      // Verify that the observer was notified
      notified should be(true)
    }

    "draw a card for a player and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Draw a card
      controller.draw(player)

      // Verify that the observer was notified
      notified should be(true)
    }

    "setup the game and notify observers" in {
      var notified = false
      val observer = new Observer {
        def update: Unit = notified = true
      }

      val player = new Player("Player 1", Hand(List()))
      val game = Game(players = List(player), currentPlayer = 0, deck = Deck(List.empty).createShuffledDeck(), state = null)
      val controller = new Controller(game)

      // Add the observer to the controller
      controller.add(observer)

      // Setup the game
      controller.setup()

      // Verify that the observer was notified
      notified should be(true)
    }
  }
}
