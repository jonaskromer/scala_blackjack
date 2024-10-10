enum Suit:
  case Hearts, Diamonds, Clubs, Spades

enum Rank(val value: Int):
  case Two extends Rank(2)
  case Three extends Rank(3)
  case Four extends Rank(4)
  case Five extends Rank(5)
  case Six extends Rank(6)
  case Seven extends Rank(7)
  case Eight extends Rank(8)
  case Nine extends Rank(9)
  case Ten extends Rank(10)
  case Jack extends Rank(10)
  case Queen extends Rank(10)
  case King extends Rank(10)
  case Ace extends Rank(11) // later eval in hand if counts 1 or 11


case class Card(rank: Rank, suit: Suit):
  override def toString = s"$rank of $suit, Value: ${rank.value}"
