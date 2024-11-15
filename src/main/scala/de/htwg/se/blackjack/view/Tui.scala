package de.htwg.se.blackjack.view

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.view.ConsoleColors.colorize
import de.htwg.se.blackjack.model.state.{EvaluateState, FinishedState, PlayerState, PreInitState}
import de.htwg.se.blackjack.util.Observer

class Tui(controller: Controller) extends Observer:

  controller.add(this)

  private def invalidAction(action: String): Unit =
    println(colorize(s"Cannot $action in ${controller.game.state}", ConsoleColors.RED))

  def getHelpString(controller: Controller): String = {
    val commands = List(
      Some("Available commands:\n"),
      if (controller.game.state.canAddPlayer) Some("add <name> <name> ... - add players") else None,
      if (controller.game.state.canStartGame()) Some("start - start the game") else None,
      if (controller.game.state.canHit) Some("hit - draw a card") else None,
      if (controller.game.state.canStand) Some("stand - end turn") else None,
      Some(
        """|printHands - print all hands
           |exit - exit the game
           |help - show this help
           |restart - restart the game
           |""".stripMargin
      )
    )

    commands.flatten.mkString("\n")
  }


  def processInput(input: String): Unit =

    val in = input.split(" ").toList
    
    in.head match
      case "help" =>
        val helpString = getHelpString(controller)
        println(helpString)


      case "add" =>
        if (!controller.game.state.canAddPlayer)
          invalidAction("add player")
          else

          val playerNames = in.drop(1)
          if (playerNames.isEmpty)
            println(colorize("Usage: add <name>", ConsoleColors.RED))
            else

            playerNames.foreach { name =>
              println(colorize(s"Added player $name", ConsoleColors.BRIGHT_BLACK))
              controller.addPlayer(name)
              Thread.sleep(500)
            }
        

      case "start" =>
        if (controller.game.players.length == 1)
          println(colorize("Add at least one player to start the game", ConsoleColors.RED))
          else

          if (!controller.game.state.canStartGame())
            invalidAction("start game")
            else controller.startGame()


      case "restart" =>
        controller.restart()


      case "hit" =>
        if (!controller.game.state.canHit)
          invalidAction("hit")
        else

          val playerBeforeHit = controller.game.currentPlayer
          controller.hit()

          if (playerBeforeHit == controller.game.currentPlayer)
            println(s"${controller.game.players(controller.game.currentPlayer).name} drew a ${controller.game.players(controller.game.currentPlayer).hand.cards.last}")
            else
            println(s"${controller.game.players(playerBeforeHit).name} drew a ${controller.game.players(playerBeforeHit).hand.cards.last}")
            println(s"next player: ${controller.game.players(controller.game.currentPlayer).name}")


      case "stand" =>
        if (!controller.game.state.canStand)
          invalidAction("stand")
        else

          println(s"${controller.game.players(controller.game.currentPlayer).name} stands")
          println(s"next player: ${controller.game.players(controller.game.getNextPlayer).name}")
          controller.stand()


      case "printHands" =>
        println()
        for (player <- controller.game.players)
          printf(s"%-15s${player.hand}", player.name + ": " )


      case "exit" =>
        println("Exiting game...")


      case _ =>
        println(colorize("Invalid command. Type 'help' for a list of commands", ConsoleColors.RED))


  override def update: Unit =
    controller.game.state match
      case _: PreInitState =>
        println("Welcome to BlackJack!")
        println("Start by adding players via add <name> <name> ...")
        println("Type 'help' for a list of commands")
        controller.game.state.execute(controller)

      case _: PlayerState =>
        println(controller.game)
        println(s"Current Player: ${controller.game.players(controller.game.currentPlayer).name}")
        println("-----------------------------------------------------------------------------------------------------------")

      case _: EvaluateState =>
        println("Evaluating game state...")
        controller.game.state.execute(controller)

      case _: FinishedState =>
        println("Game finished")

      case _ =>
        println(controller.game)

