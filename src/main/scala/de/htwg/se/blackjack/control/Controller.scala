package de.htwg.se.blackjack.control

import de.htwg.se.blackjack.model.Game
import de.htwg.se.blackjack.util.Observable

class Controller(var game: Game) extends Observable {
  
  def addPlayer(name: String): Unit = {
    game = game.addPlayer(name)
    notifyObservers
  }
  
  def startGame(): Unit = {
    game = game.startGame()
    notifyObservers
  }
  
  def hit(): Unit = {
    game = game.hit()
    notifyObservers
  }
  
  def stand(): Unit = {
    game = game.stand()
    notifyObservers
  }
  
  def restart(): Unit = {
    game = game.restart()
    notifyObservers
  }
  
  def dealerDraw(): Unit = {
    game = game.dealerDraw()
    notifyObservers
  }
  
  def evalGame(): Unit = {
    game = game.evalGame()
    notifyObservers
  }

}
