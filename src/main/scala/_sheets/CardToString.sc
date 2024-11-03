import model.Card

enum Suit:
  case Hearts, Diamonds, Clubs, Spades

enum Rank(val value: Int):
  case Two extends model.Rank(2)
  case Three extends model.Rank(3)
  case Four extends model.Rank(4)
  case Five extends model.Rank(5)
  case Six extends model.Rank(6)
  case Seven extends model.Rank(7)
  case Eight extends model.Rank(8)
  case Nine extends model.Rank(9)
  case Ten extends model.Rank(10)
  case Jack extends model.Rank(10)
  case Queen extends model.Rank(10)
  case King extends model.Rank(10)
  case Ace extends model.Rank(11) // later eval in hand if counts 1 or 11


case class Card(rank: model.Rank, suit: model.Suit):
  override def toString = s"$rank of $suit, |Value: ${rank.value}|"

val card1 = Card(Rank.Jack, Suit.Diamonds)