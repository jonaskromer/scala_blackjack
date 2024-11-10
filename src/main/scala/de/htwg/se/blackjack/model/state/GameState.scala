package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.view.Tui
import de.htwg.se.blackjack.control.Controller

trait GameState {
  def execute(controller: Controller): Unit
}
