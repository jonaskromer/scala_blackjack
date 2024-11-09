package view

import model.Game
import model.GameState.EXIT
import view.ConsoleColors.colorize

class Tui {
  def processInput(input: String, game: Game): Game = 
    
    val in = input.split(" ").toList
    
    in(0) match

      case "help" =>
        println("TODO")
        game.copy()
      
      case "add" =>
        println(colorize(s"Added player ${in(1)}", ConsoleColors.BRIGHT_BLACK))
        game.addPlayer(in(1))
      
      case "start" => 
        game.startGame()
        
      case "restart" => 
        game.restart()
      
      case "hit" => 
        game.hit()
        
      case "stand" => 
        game.stand()
      
      case "printHands" =>
        println()
        for (player <- game.players)
          printf(s"%-15s${player.hand}", player.name + ": " )

        game.copy()

      case "exit" =>
        println("Exiting game...")
        game.copy(state = EXIT)
      
      case _ => game
    
}
