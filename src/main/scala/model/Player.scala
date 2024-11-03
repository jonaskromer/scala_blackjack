package model

case class Player(name: String, hand: Hand):
  
  def printHand: String = s"$name: $hand"