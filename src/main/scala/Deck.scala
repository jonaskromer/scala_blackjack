import scala.util.Random

case class Deck(cards: List[Card]):
  // Method to create a shuffled deck with an optional seed
  def createShuffledDeck(seed: Option[Long] = None): Deck =
    val allCards = for {
      suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)
      rank <- Rank.values.toList
    } yield Card(rank, suit)

    // Create a Random instance with the provided seed
    val random = seed match
      case Some(s) => new Random(s)  // Create a Random object with the given seed
      case None    => new Random()    // Create a Random object without a seed


    Deck(random.shuffle(allCards))  // Shuffle the cards and return a new Deck

  // Function to draw a card from the deck
  def draw: Option[(Card, Deck)] = cards match
    case Nil => None  // If deck is empty, return None
    case head :: tail => Some(head, Deck(tail))  // Draw the top card and return it with the rest of the deck

// Function to draw a number of cards from the deck
def drawCardsFromDeck(deck: Deck, numberOfCards: Int): Unit =
  var currentDeck = deck // Use a mutable variable to keep track of the current deck
  for (i <- 1 to numberOfCards)
    currentDeck.draw match {
      case Some((card, remainingDeck)) =>
        println(s"\nDrawn Card: $card")
        currentDeck = remainingDeck // Update the current deck to the remaining deck
      case None =>
        println("\nNo cards left in the deck.")
        scala.util.boundary // Exit the loop if there are no more cards
    }


  println(s"Cards remaining in the deck: ${currentDeck.cards.size}\n")
// deck = currentDeck

