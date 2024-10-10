case class Hand(cards: List[Card], name: String):
  // Method to calculate total hand value
  def totalValue: Int =
    // Count number of Aces in the hand
    val (aceCount, initialValue) = cards.foldLeft((0, 0)) {
      case ((aCount, total), Card(Rank.Ace, _)) => (aCount + 1, total + Rank.Ace.value)
      case ((aCount, total), card) => (aCount, total + card.rank.value)
    }

    // Subtract one ace if total value is over 21
    if (initialValue > 21)
      return initialValue - 10

    initialValue // Return if no ace spawned

  override def toString: String = s"$cards, \n Total Value: $totalValue"

