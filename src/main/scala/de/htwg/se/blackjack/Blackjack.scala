package de.htwg.se.blackjack

import de.htwg.se.blackjack.control.Controller
import de.htwg.se.blackjack.view.Tui
import de.htwg.se.blackjack.model.*
import de.htwg.se.blackjack.model.state.InitState

import scala.io.StdIn.readLine

@main def main(): Unit =

  val game = Game(List.empty, 0, Deck(List.empty).createShuffledDeck(), InitState()).restart()
  
  val controller = new Controller(game)
  val tui = new Tui(controller)

  var input = ""

  while (input != "exit")
    input = readLine()
    tui.processInput(input) 
