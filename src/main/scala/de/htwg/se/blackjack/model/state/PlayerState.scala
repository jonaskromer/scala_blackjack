package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class PlayerState extends GameState:
  
  override def execute(controller: Controller): Unit = 
    if (controller.game.currentPlayer == 0) {
      controller.game = controller.game.copy(state = DealerState())
    } else {
      controller.game = controller.game.copy(state = PlayerState())
    }
    
  override def canAddPlayer: Boolean = false
  
  override def canStartGame(): Boolean = false
  
  override def canHit: Boolean = true
  
  override def canStand: Boolean = true
  
  override def toString: String = "player state"
