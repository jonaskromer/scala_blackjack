package de.htwg.se.blackjack.model

enum Suit:
  case Hearts, Diamonds, Clubs, Spades

  override def toString: String = this match
    case Hearts => "\u2665" // ♥
    case Diamonds => "\u2666" // ♦
    case Clubs => "\u2663" // ♣
    case Spades => "\u2660" // ♠


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
  case Ace extends Rank(11)

  override def toString: String = this match
    case Two   => "2️⃣"
    case Three => "3️⃣"
    case Four  => "4️⃣"
    case Five  => "5️⃣"
    case Six   => "6️⃣"
    case Seven => "7️⃣"
    case Eight => "8️⃣"
    case Nine  => "9️⃣"
    case Ten   => "🔟"
    case Jack  => "🃏"
    case Queen => "👸"
    case King  => "🤴"
    case Ace   => "🅰️"



case class Card(rank: Rank, suit: Suit):
  override def toString: String = s"$rank of $suit, Value: ${"%02d".format(rank.value)}"
