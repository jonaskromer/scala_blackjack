// Import necessary library
import scala.util.Random

// Define Suit
enum Suit:
  case Hearts, Diamonds, Clubs, Spades

// Define Rank
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

// Define Card
case class Card(rank: Rank, suit: Suit):
  override def toString: String = s"$rank of $suit, Value: ${rank.value}"

// Define Deck
case class Deck(cards: List[Card]) {
  // Method to create a shuffled deck with an optional seed
  def createShuffledDeck(seed: Option[Long] = None): Deck = {
    val allCards = for {
      suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)
      rank <- Rank.values.toList
    } yield Card(rank, suit)

    // Create a Random instance with the provided seed
    val random = seed match {
      case Some(s) => new Random(s)  // Create a Random object with the given seed
      case None    => new Random()    // Create a Random object without a seed
    }

    Deck(random.shuffle(allCards))  // Shuffle the cards and return a new Deck
  }

  // Function to draw a card from the deck
  def draw: Option[(Card, Deck)] = cards match {
    case Nil => None  // If deck is empty, return None
    case head :: tail => Some(head, Deck(tail))  // Draw the top card and return it with the rest of the deck
  }
}

// Create and shuffle a deck
val initialDeck = Deck(Nil).createShuffledDeck()

// Drawing cards
var currentDeck = initialDeck // Use a mutable variable to keep track of the current deck

println("Drawing cards from the deck:")

// Draw 5 cards from the deck
for (_ <- 1 to 5) {
  currentDeck.draw match {
    case Some((card, remainingDeck)) =>
      println(s"Drawn Card: $card")
      currentDeck = remainingDeck  // Update the current deck to the remaining deck
    case None =>
      println("No cards left in the deck.")
    // Optionally break the loop if you run out of cards
    // break
  }
}

println(s"Cards remaining in the deck: ${currentDeck.cards.size}")
