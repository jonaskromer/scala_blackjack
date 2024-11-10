package de.htwg.se.blackjack.model.state

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.view.ConsoleColors.colorize
import de.htwg.se.blackjack.view.{ConsoleColors, Tui}

import scala.io.StdIn.readLine

class InitState extends GameState:
  
  override def execute(controller: Controller): Unit = {}

