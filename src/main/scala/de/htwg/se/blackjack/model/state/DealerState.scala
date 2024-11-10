package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class DealerState extends GameState:
  
  override def execute(controller: Controller): Unit = 
    controller.dealerDraw()
  
  override def canAddPlayer: Boolean = false
  
  override def canStartGame(): Boolean = false
  
  override def canHit: Boolean = false
  
  override def canStand: Boolean = false
  
  override def toString: String = "dealer state"

