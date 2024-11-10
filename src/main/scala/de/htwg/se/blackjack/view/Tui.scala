package de.htwg.se.blackjack.view

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.model.Game
import de.htwg.se.blackjack.view.ConsoleColors.colorize
import de.htwg.se.blackjack.model.GameState.*
import de.htwg.se.blackjack.util.Observer

class Tui(controller: Controller) extends Observer:
  def processInput(input: String, game: Game): Unit =

    controller.add(this)
    
    val in = input.split(" ").toList
    
    in(0) match

      case "help" =>
        println("TODO")
      
      case "add" =>
        println(colorize(s"Added player ${in(1)}", ConsoleColors.BRIGHT_BLACK))
        controller.addPlayer(in(1))

      case "start" =>
        controller.game = controller.game.copy(state = DISTRIBUTE)
        controller.startGame()
        
      case "restart" => 
        controller.restart()

      case "hit" =>
        val playerBeforeHit = controller.game.currentPlayer
        controller.hit()
        if (playerBeforeHit == controller.game.currentPlayer)
          println(s"${controller.game.players(controller.game.currentPlayer).name} drew a ${controller.game.players(controller.game.currentPlayer).hand.cards.last}")
        else
          println(s"${controller.game.players(controller.game.currentPlayer - 1).name} drew a ${controller.game.players(controller.game.currentPlayer - 1).hand.cards.last}")
          println(s"next player: ${controller.game.players(controller.game.currentPlayer).name}")

      case "stand" =>
        println(s"${controller.game.players(controller.game.currentPlayer).name} stands")
        println(s"next player: ${controller.game.players(controller.game.getNextPlayer).name}")
        controller.stand()
      
      case "printHands" =>
        println()
        for (player <- game.players)
          printf(s"%-15s${player.hand}", player.name + ": " )

      case "exit" =>
        println("Exiting game...")
      
      case _ => game


  override def update: Unit = println(controller.game)