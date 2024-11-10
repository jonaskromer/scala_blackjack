package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class FinishedState extends GameState:
  
  override def execute(controller: Controller): Unit = {}
  
  override def canAddPlayer: Boolean = false
  
  override def canStartGame(): Boolean = false
  
  override def canHit: Boolean = false
  
  override def canStand: Boolean = false
  
  override def toString: String = "finished state"
