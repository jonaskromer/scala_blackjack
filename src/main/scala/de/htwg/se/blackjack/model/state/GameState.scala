package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

trait GameState:
  
  def execute(controller: Controller): Unit
  
  def canAddPlayer: Boolean
  
  def canStartGame(): Boolean
  
  def canHit: Boolean
  
  def canStand: Boolean
