// Import necessary library
import scala.util.Random

// Define Suit
enum Suit:
  case Hearts, Diamonds, Clubs, Spades

// Define Rank
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
  case Ace extends model.Rank(11)

// Define Card
case class Card(rank: model.Rank, suit: model.Suit):
  override def toString: String = s"$rank of $suit, Value: ${rank.value}"

// Define Deck
case class Deck(cards: List[model.Card]) {
  // Method to create a shuffled deck with an optional seed
  def createShuffledDeck(seed: Option[Long] = None): model.Deck = {
    val allCards = for {
      suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)
      rank <- Rank.values.toList
    } yield model.Card(rank, suit)

    // Create a Random instance with the provided seed
    val random = seed match {
      case Some(s) => new Random(s)  // Create a Random object with the given seed
      case None    => new Random()    // Create a Random object without a seed
    }

    model.Deck(random.shuffle(allCards))  // Shuffle the cards and return a new Deck
  }

  // Function to draw a card from the deck
  def draw: Option[(model.Card, model.Deck)] = cards match {
    case Nil => None  // If deck is empty, return None
    case head :: tail => Some(head, model.Deck(tail))  // Draw the top card and return it with the rest of the deck
  }
}

// Create and shuffle a deck
val initialDeck = model.Deck(Nil).createShuffledDeck()

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
