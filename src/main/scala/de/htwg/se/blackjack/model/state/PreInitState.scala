package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class PreInitState extends GameState:

  override def execute(controller: Controller): Unit =
    controller.game = controller.game.copy(state = InitState())

  override def canAddPlayer: Boolean = true

  override def canStartGame(): Boolean = false

  override def canHit: Boolean = false

  override def canStand: Boolean = false

  override def toString: String = " pre initial state"