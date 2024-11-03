package view

import model.Game

class Tui {
  def processInput(input: String, game: Game): Game = {
    val in = input.split(" ").toList
    in(0) match {
      case "add" => game.addPlayer(in(1))
      case "start" => game.startGame()
      case "restart" => game.restart()
      case "hit" => game.hit()
      case "stand" => game.stand()
      case "printHands" => game.printHands()
      case _ => game
    }
  }
}
