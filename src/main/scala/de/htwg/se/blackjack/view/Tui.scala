package de.htwg.se.blackjack.view

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.view.ConsoleColors.colorize
import de.htwg.se.blackjack.model.state.{DealerState, DistributeState, EvaluateState, GameState, InitState, PlayerState}
import de.htwg.se.blackjack.util.Observer

class Tui(controller: Controller) extends Observer:

  controller.add(this)

  def processInput(input: String): Unit =

    val in = input.split(" ").toList
    
    in(0) match

      case "help" =>
        println("TODO")
      
      case "add" =>
        if (!controller.game.state.isInstanceOf[InitState])
          println(colorize("Cannot add player after game has started", ConsoleColors.RED))
          return

        println(colorize(s"Added player ${in(1)}", ConsoleColors.BRIGHT_BLACK))
        controller.addPlayer(in(1))


      case "start" =>
        if (!controller.game.state.isInstanceOf[InitState])
          println(colorize("Game has already started", ConsoleColors.RED))
          return

        controller.startGame()
        
      case "restart" => 
        controller.restart()

      case "hit" =>
        if (controller.game.state.isInstanceOf[DistributeState] || controller.game.state.isInstanceOf[InitState])
          println(colorize("Cannot hit before game has started", ConsoleColors.RED))
          return

        if (!controller.game.state.isInstanceOf[PlayerState])
          println(colorize("Cannot hit", ConsoleColors.RED))
          return

        val playerBeforeHit = controller.game.currentPlayer
        controller.hit()
        controller.game.state.execute(controller)

        if (playerBeforeHit == controller.game.currentPlayer)
          println(s"${controller.game.players(controller.game.currentPlayer).name} drew a ${controller.game.players(controller.game.currentPlayer).hand.cards.last}")
        else
          println(s"${controller.game.players(playerBeforeHit).name} drew a ${controller.game.players(playerBeforeHit).hand.cards.last}")
          println(s"next player: ${controller.game.players(controller.game.currentPlayer).name}")

      case "stand" =>
        if (controller.game.state.isInstanceOf[DistributeState] || controller.game.state.isInstanceOf[InitState])
          println(colorize("Cannot stand before game has started", ConsoleColors.RED))
          return

        if (!controller.game.state.isInstanceOf[PlayerState])
          println(colorize("Cannot stand", ConsoleColors.RED))
          return

        println(s"${controller.game.players(controller.game.currentPlayer).name} stands")
        println(s"next player: ${controller.game.players(controller.game.getNextPlayer).name}")
        controller.stand()
        controller.game.state.execute(controller)

      case "printHands" =>
        println()
        for (player <- controller.game.players)
          printf(s"%-15s${player.hand}", player.name + ": " )

      case "exit" =>
        println("Exiting game...")
      
      case _ => 


  override def update: Unit =
    
    if (controller.game.state.isInstanceOf[EvaluateState])
      controller.game.state.execute(controller)
      
    println(controller.game)