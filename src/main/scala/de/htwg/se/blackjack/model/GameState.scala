package de.htwg.se.blackjack.model

enum GameState:
  case INIT, DISTRIBUTE, PLAYER, DEALER, EVALUATE, FINISHED, EXIT
