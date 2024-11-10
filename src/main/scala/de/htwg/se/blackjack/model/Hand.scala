package de.htwg.se.blackjack.model

import de.htwg.se.blackjack.view.ConsoleColors
import de.htwg.se.blackjack.view.ConsoleColors.colorize

case class Hand(cards: List[Card]):

  def addCard(card: Card): Hand = Hand(cards :+ card)
  
  def totalValue: Int =
    
    val (aceCount, initialValue) = cards.foldLeft((0, 0)):
      case ((aCount, total), Card(Rank.Ace, _)) => (aCount + 1, total + Rank.Ace.value)
      case ((aCount, total), card) => (aCount, total + card.rank.value)

//    if (initialValue > 21)
//      return initialValue - 10

    initialValue

  override def toString: String = 
    val totalWidth = 20

    val formattedCards = cards.map: card =>
      val cardStr = card.toString
      if (card.rank.value < 9) String.format(s"%-${totalWidth + 1}s", cardStr)
      else String.format(s"%-${totalWidth}s", cardStr)

    val cardString = formattedCards.mkString("| ")

    if (cards.isEmpty) return colorize("Empty Hand\n", ConsoleColors.BRIGHT_GREEN)

    colorize(s"Total Value: ${f"$totalValue%02d"} | $cardString\n", ConsoleColors.BRIGHT_GREEN)
