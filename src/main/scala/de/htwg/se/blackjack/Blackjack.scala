package de.htwg.se.blackjack

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.{Deck, Game}
import de.htwg.se.blackjack.view.{ConsoleColors, Tui}
import de.htwg.se.blackjack.model.*
import de.htwg.se.blackjack.model.GameState.*
import de.htwg.se.blackjack.view.ConsoleColors.colorize
import de.htwg.se.blackjack.view.ConsoleColors

import scala.io.StdIn.readLine


@main def main(): Unit =
  println(colorize("Welcome to Blackjack!", ConsoleColors.BRIGHT_CYAN))

  val deck = Deck(List.empty).createShuffledDeck()
  var game = Game(List.empty, 0, deck, INIT).restart()

  val controller = new Controller(game)
  val tui = new Tui(controller)

  var input = ""
  
  while (controller.game.state != EXIT) {

    while (controller.game.state == INIT) {
      println("\nType " + colorize("'add NAME'", ConsoleColors.BRIGHT_WHITE) + " to add a player or " + colorize("'start'", ConsoleColors.BRIGHT_WHITE) + " to start the game")
      input = readLine()
      tui.processInput(input, game)
    }
    while (controller.game.state == DISTRIBUTE) {
      var gameState = game
      for (i <- 1 to 2) {
        for (player <- gameState.players) {
          gameState = gameState.drawCard(player, gameState.deck)
          Thread.sleep(500)
        }
      }
      tui.processInput("printHands", gameState)
      controller.game = gameState.copy(gameState.players, 1, gameState.deck, state = PLAYER) //skip dealer
    }

    while (controller.game.state == PLAYER) {
      println("\nHello " + colorize(s"${game.players(game.currentPlayer).name}", ConsoleColors.BRIGHT_WHITE) + ", what would you like to do?")
      input = readLine("Options: " + colorize("'hit'", ConsoleColors.BRIGHT_WHITE) + ", "
        + colorize("'stand'", ConsoleColors.BRIGHT_WHITE) + ", "
        + colorize("'printHands'", ConsoleColors.BRIGHT_WHITE) + ", "
        + colorize("'exit'", ConsoleColors.BRIGHT_WHITE)+ "\n")
      tui.processInput(input, game)
    }

    while (controller.game.state == DEALER) {
      controller.game = controller.game.dealerDraw()
    }

    while (controller.game.state == EVALUATE) {
      controller.game = controller.game.evalGame()
    }

    while (controller.game.state == FINISHED) {
      println("\nType " + colorize("'restart'", ConsoleColors.BRIGHT_WHITE) + " to play again or " + colorize("'exit'", ConsoleColors.BRIGHT_WHITE) + " to quit")
      input = readLine()
      tui.processInput(input, game)
    }
  }
