package de.htwg.se.blackjack.control

import de.htwg.se.blackjack.model.{Game, Player}
import de.htwg.se.blackjack.util.Observable

class Controller(var game: Game) extends Observable {
  
  def addPlayer(name: String): Unit = {
    game = game.addPlayer(name)
    notifyObservers
  }
  
  def startGame(): Unit = {
    game = game.startGame()
    game.state.execute(this)
    notifyObservers
  }
  
  def hit(): Unit = {
    game = game.hit()
    game.state.execute(this)
    notifyObservers
  }
  
  def stand(): Unit = {
    game = game.stand()
    game.state.execute(this)
    notifyObservers
  }
  
  def restart(): Unit = {
    game = game.setup()
    notifyObservers
  }
  
  def dealerDraw(): Unit = {
    game = game.dealerDraw()
    //notifyObservers
  }
  
  def evalGame(): Unit = {
    game = game.evalGame()
    notifyObservers
  }

  def draw(player: Player): Unit = {
    game = game.drawCard(player, game.deck)
    notifyObservers
  }
  
  def setup(): Unit = {
    game = game.setup()
    notifyObservers
  }

}
