package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class InitState extends GameState:
  
  override def execute(controller: Controller): Unit = {}
  
  override def canAddPlayer: Boolean = true
  
  override def canStartGame(): Boolean = true
  
  override def canHit: Boolean = false
  
  override def canStand: Boolean = false
  
  override def toString: String = "initial state"