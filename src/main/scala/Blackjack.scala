import model.GameState.*
import model.*
import view.ConsoleColors.colorize
import view.{ConsoleColors, Tui}

import scala.io.StdIn.readLine


@main def main(): Unit =
  println(colorize("Welcome to Blackjack!", ConsoleColors.BRIGHT_CYAN))

  val deck = Deck(List.empty).createShuffledDeck()
  var game = Game(List.empty, 0, deck, INIT).restart()
  val tui = new Tui

  var input = ""
  
  while (game.state != EXIT) {

    while (game.state == INIT) {
      println("\nType " + colorize("'add NAME'", ConsoleColors.BRIGHT_WHITE) + " to add a player or " + colorize("'start'", ConsoleColors.BRIGHT_WHITE) + " to start the game")
      input = readLine()
      game = tui.processInput(input, game)
    }
    while (game.state == DISTRIBUTE) {
      var gameState = game
      for (i <- 1 to 2) {
        for (player <- gameState.players) {
          gameState = gameState.drawCard(player, gameState.deck)
          Thread.sleep(500)
        }
      }
      tui.processInput("printHands", gameState)
      game = gameState.copy(gameState.players, 1, gameState.deck, state = PLAYER) //skip dealer
    }

    while (game.state == PLAYER) {
      println("\nHello " + colorize(s"${game.players(game.currentPlayer).name}", ConsoleColors.BRIGHT_WHITE) + ", what would you like to do?")
      input = readLine("Options: " + colorize("'hit'", ConsoleColors.BRIGHT_WHITE) + ", "
        + colorize("'stand'", ConsoleColors.BRIGHT_WHITE) + ", "
        + colorize("'printHands'", ConsoleColors.BRIGHT_WHITE) + ", "
        + colorize("'exit'", ConsoleColors.BRIGHT_WHITE)+ "\n")
      game = tui.processInput(input, game)
    }

    while (game.state == DEALER) {
      game = game.dealerDraw()
    }

    while (game.state == EVALUATE) {
      game = game.evalGame()
    }

    while (game.state == FINISHED) {
      println("\nType " + colorize("'restart'", ConsoleColors.BRIGHT_WHITE) + " to play again or " + colorize("'exit'", ConsoleColors.BRIGHT_WHITE) + " to quit")
      input = readLine()
      game = tui.processInput(input, game)
    }
  }
