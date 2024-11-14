package de.htwg.se.blackjack.model

case class Player(name: String, hand: Hand):

  def printHand: String = f"$name%15s: $hand"