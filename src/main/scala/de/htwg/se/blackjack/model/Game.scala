package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.model.GameState.*

import scala.annotation.tailrec


case class Game(players: List[Player], currentPlayer: Int, deck: Deck, state: GameState):
  
  def copy(players: List[Player] = this.players,
           currentPlayer: Int = this.currentPlayer,
           deck: Deck = this.deck,
           state: GameState = this.state): Game = Game(players, currentPlayer, deck, state)
  
  def addPlayer(name: String): Game =
    copy(players :+ Player(name, Hand(List.empty)))

  def startGame(): Game = 
    copy(state = DISTRIBUTE)

  def drawCard(player: Player, deck: Deck): Game = 
    val (drawnCard, remainingDeck) = deck.draw()
    val newPlayer = player.copy(hand = player.hand.addCard(drawnCard.get)) // TODO: handle None case
    copy(players.map(p => if (p == player) newPlayer else p), evalOverbuy(newPlayer), remainingDeck)

  def hit(): Game = 
    drawCard(players(currentPlayer), deck).evalIfDealer()
  
  def stand(): Game = 
    copy(players, getNextPlayer).evalIfDealer()
  
  def getNextPlayer: Int = if (currentPlayer + 1 < players.length) 
    currentPlayer + 1
  else 0

  def evalOverbuy(player: Player): Int = 
    if (player.hand.totalValue > 21)
      return getNextPlayer
    currentPlayer

  def evalIfDealer(): Game = 
    if (currentPlayer == 0) 
      copy(state = GameState.DEALER)
    else 
      this

  def dealerDraw(): Game = 

    @tailrec
    def drawUntilStand(game: Game): Game = 
      if (game.players(0).hand.totalValue < 17) 
        val updatedGame = game.drawCard(game.players(0), game.deck)
        drawUntilStand(updatedGame)
      else 
        game.copy(state = GameState.EVALUATE)

    drawUntilStand(this)
  
  def evalGame(): Game = 
    val dealer = players(0)
    val dealerValue = dealer.hand.totalValue
    val playerValues = players.tail.map(p => p.hand.totalValue)
    val playerNames = players.tail.map(p => p.name)
    val playerResults = playerValues.map(v => if (v > 21) -1 else v)
    val dealerResult = if (dealerValue > 21) -1 else dealerValue
    val results = playerResults :+ dealerResult
    val winners = playerNames.zip(results).filter(_._2 == results.max).map(_._1)
    println(s"Results: ${playerNames.zip(results).mkString(", ")}")
    println(s"Winners: ${winners.mkString(", ")}")
    copy(state = FINISHED)

  def restart(): Game = 
    val newDeck = Deck(List.empty).createShuffledDeck()
    val newPlayers = List(Player("DEALER", Hand(List.empty)))
    Game(newPlayers, 0, newDeck, INIT)
