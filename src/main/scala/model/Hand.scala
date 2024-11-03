package model

import view.ConsoleColors
import view.ConsoleColors._

case class Hand(cards: List[Card]):

  def addCard(card: Card): Hand = Hand(cards :+ card)
  
  def totalValue: Int =
    
    val (aceCount, initialValue) = cards.foldLeft((0, 0)) {
      case ((aCount, total), Card(Rank.Ace, _)) => (aCount + 1, total + Rank.Ace.value)
      case ((aCount, total), card) => (aCount, total + card.rank.value)
    }

//    if (initialValue > 21)
//      return initialValue - 10

    initialValue

  override def toString: String =
    colorize(s"Total Value: " + f"$totalValue%02d" + " | " + cards.mkString(", ") + "\n", ConsoleColors.BRIGHT_GREEN)