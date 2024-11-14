package de.htwg.se.blackjack.view

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.Suit.Spades
import de.htwg.se.blackjack.model.state.{EvaluateState, FinishedState, InitState, PlayerState, PreInitState}
import de.htwg.se.blackjack.model.{Card, Deck, Game, Hand, Player, Rank, Suit}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayOutputStream, PrintStream}

class TuiSpec extends AnyWordSpec with Matchers {

  "The Tui" should {

    "print the help message correctly" in {
      val deck = Deck(List.empty).createShuffledDeck() // Assuming a simple deck initialization
      val players = List[Player]() // Empty list of players for the pre-init state
      var game = new Game(players, 0, deck, InitState())
      val controller = new Controller(game)
      val tui = new Tui(controller)


      val output = captureOutput {
        tui.processInput("help")
      }
      
      output should include("Available commands:")
      output should include("add <name> <name> ... - add players")
      output should include("start - start the game")
      output should include("restart - restart the game")
    }

    "print the help message correctly if playerstate" in {
      val deck = Deck(List.empty).createShuffledDeck() // Assuming a simple deck initialization
      val players = List[Player]() // Empty list of players for the pre-init state
      var game = new Game(players, 0, deck, PlayerState())
      val controller = new Controller(game)
      val tui = new Tui(controller)


      val output = captureOutput {
        tui.processInput("help")
      }

      output should include("hit - draw a card")
      output should include("stand - end turn")
    }

    "add players correctly when 'add' command is issued" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PreInitState()
      val players = List[Player]() // Initially no players
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      // Simulating game state
      game = game.copy(state = new PreInitState())

      val output = captureOutput {
        tui.processInput("add Alice Bob")
      }

      output should include("Added player Alice")
      output should include("Added player Bob")
    }

    "display usage when add is used wrong" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PreInitState()
      val players = List[Player]() // Initially no players
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      // Simulating game state
      game = game.copy(state = new PreInitState())

      val output = captureOutput {
        tui.processInput("add")
      }

      output should include("Usage: add <name>")
    }

    "print invalid action when game is not startable" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PreInitState()
      val players = List[Player]() // Initially no players
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      // Simulating game state
      game = game.copy(state = new PreInitState())

      val output = captureOutput {
        tui.processInput("start")
      }

      output should include("Cannot start game in ")
    }

    "print next player when p1 overbought with hit" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PlayerState()
      val players = List[Player](Player("DEALER", Hand(List(Card(Rank.Ten, Suit.Hearts)))), Player("p1", Hand(List(Card(Rank.Ten, Suit.Hearts), Card(Rank.Ten, Spades)))), Player("p2", Hand(List(Card(Rank.Ten, Suit.Hearts)))))
      var game = new Game(players, 1, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      val output = captureOutput {
        tui.processInput("hit")
      }

      output should include("p1 drew a")
      output should include("next player: p2")
    }

    "not allow 'add' command if game cannot add players" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PlayerState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      // Simulate the PlayerState where players cannot be added
      game = game.copy(state = new PreInitState())

      val output = captureOutput {
        tui.processInput("add Alice")
      }

      output should include("Cannot add player in ")
    }

    "apply restart correctly" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new FinishedState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts))))
      )
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      game = game.copy(state = new FinishedState())

      val output = captureOutput {
        tui.processInput("restart")
      }

      output should include("Welcome")
    }

    "start the game correctly when 'start' command is issued" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new InitState
      val players = List[Player](Player("Dealer", Hand(List(Card(Rank.Two, Suit.Hearts)))), Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      val output = captureOutput {
        tui.processInput("start")
      }

      output should include("Current Player: Alice")
    }

    "not allow 'start' command if no players are added" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PreInitState()
      val players = List[Player](Player("DEALER", Hand(List.empty)))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      val output = captureOutput {
        tui.processInput("start")
      }

      output should include("Add at least one player to start the game")
    }

    "execute the 'hit' command when valid" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PlayerState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      game = game.copy(state = new PlayerState())

      val output = captureOutput {
        tui.processInput("hit")
      }

      output should include("Alice drew a ")
    }

    "not allow 'hit' command if not allowed" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new FinishedState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      game = game.copy(state = new FinishedState())

      val output = captureOutput {
        tui.processInput("hit")
      }

      output should include("Cannot hit in ")
    }

    "handle 'stand' correctly" in {
      val deck = Deck(List.empty).createShuffledDeck()

      val gameState = new PlayerState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      game = game.copy(state = new PlayerState())


      val output = captureOutput {
        tui.processInput("stand")
      }

      output should include("stands")
    }

    "not allow 'stand' command if not allowed" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new FinishedState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      game = game.copy(state = new FinishedState())


      val output = captureOutput {
        tui.processInput("stand")
      }

      output should include("Cannot stand in ")
    }

    "print all hands correctly when 'printHands' is issued" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PlayerState()
      val players = List[Player](
        Player("Alice", Hand(List(Card(Rank.Ace, Suit.Spades)))),
        Player("Bob", Hand(List(Card(Rank.Two, Suit.Hearts))))
      )
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      val output = captureOutput {
        tui.processInput("printHands")
      }

      output should include("Alice:")
      output should include("Total Value: 11")
      output should include("Bob:")
      output should include("Total Value: 02")
    }

    "exit the game when 'exit' is typed" in {
      val deck = Deck(List.empty).createShuffledDeck()

      val gameState = new PlayerState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      val output = captureOutput {
        tui.processInput("exit")
      }

      output should include("Exiting game...")
    }

    "handle invalid commands gracefully" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PlayerState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      val output = captureOutput {
        tui.processInput("invalidCommand")
      }

      output should include("Invalid command. Type 'help' for a list of commands")
    }

    "update the game state properly" in {
      val deck = Deck(List.empty).createShuffledDeck()
      val gameState = new PreInitState()
      val players = List[Player](Player("Alice", Hand(List(Card(Rank.Two, Suit.Hearts)))))
      var game = new Game(players, 0, deck, gameState)
      val controller = new Controller(game)
      val tui = new Tui(controller)

      game = game.copy(state = new PreInitState())

      val output = captureOutput {
        tui.update
      }

      output should include("Welcome to BlackJack!")
    }
  }

  // Helper function to capture output of a function that prints to stdout
  def captureOutput[T](block: => T): String = {
    val outStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outStream))(block)
    outStream.toString
  }
}
