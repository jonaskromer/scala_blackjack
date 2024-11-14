package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class DistributeState extends GameState:

  override def execute(controller: Controller): Unit =
    controller.game.players.foreach(player => {
      controller.draw(player)
      Thread.sleep(750)
    })
    controller.game.players.foreach(player => {
      controller.draw(player)
      Thread.sleep(750)
    })
    controller.game = controller.game.copy(currentPlayer = 1)

    controller.game = controller.game.copy(state = PlayerState())
    
  override def canAddPlayer: Boolean = false
  
  override def canStartGame(): Boolean = false
  
  override def canHit: Boolean = false
  
  override def canStand: Boolean = false
  
  override def toString: String = "distribute state"
