package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class PlayerState extends GameState:
  
  override def execute(controller: Controller): Unit = 
    if (controller.game.currentPlayer == 0) {
      controller.game = controller.game.copy(state = DealerState())
    } else {
      controller.game = controller.game.copy(state = PlayerState())
    }

