package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller

class EvaluateState extends GameState:
  
  override def execute(controller: Controller): Unit =
    controller.evalGame()