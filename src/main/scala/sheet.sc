import scala.util.Random
import scala.io.StdIn.readLine

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

val d1 = Deck(Nil).createShuffledDeck()

// Function to draw a number of cards from the deck
def drawCardsFromDeck(deck: Deck, numberOfCards: Int): Unit = {
  var currentDeck = deck // Use a mutable variable to keep track of the current deck

  println("Drawing cards from the deck:")

  for (i <- 1 to numberOfCards) {
    currentDeck.draw match {
      case Some((card, remainingDeck)) =>
        println(s"Drawn Card: $card")
        currentDeck = remainingDeck // Update the current deck to the remaining deck
      case None =>
        println("No cards left in the deck.")
        scala.util.boundary // Exit the loop if there are no more cards
    }
  }

  println(s"Cards remaining in the deck: ${currentDeck.cards.size}")
}

drawCardsFromDeck(d1,2)


// Method to create a full deck of cards
def createDeck(): List[Card] =
  for {
    suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)  // Step 1: Iterate over each Suit
    rank <- Rank.values.toList                                          // Step 2: Iterate over each Rank
  } yield Card(rank, suit)                                              // Step 3: Yield a Card combining each Rank with each Suit

def shuffleDeck(deck:List[Card]): List[Card] = Random().shuffle(deck)


val pile = createDeck()
shuffleDeck(pile)


case class Hand(cards: List[Card]):
  // Method to calculate total hand value
  def totalValue: Int =
    // Count number of Aces in the hand
    val (aceCount, initialValue) = cards.foldLeft((0, 0)) {
      case ((aCount, total), Card(Rank.Ace, _)) => (aCount + 1, total + Rank.Ace.value)
      case ((aCount, total), card) => (aCount, total + card.rank.value)
    }
    // Subtract one ace if total value is over 21
    if (initialValue > 21) {
      return initialValue - 10
    }

    initialValue // Return if no ace spawned

  override def toString: String = s"$cards, \n Total Value: $totalValue"
  //TODO ADD FUNC


val aCard = Card(Rank.Two, Suit.Hearts)
val aceCard = Card(Rank.Ace, Suit.Spades)
val anotherAce = Card(Rank.Ace, Suit.Clubs)
val aSecondCard = Card(Rank.King, Suit.Diamonds)
val aNineCard = Card(Rank.Nine, Suit.Clubs)

val cardList = List(aCard, aceCard, anotherAce, aSecondCard)
val blackjackList = List(aceCard, aSecondCard)


val playerHand = Hand(cardList)
val jackpotHand = Hand(blackjackList)

println("This is TUI-Blackjack")

val name = readLine("Enter you Name: ")

println(s"Welcome $name!")

val cardDeck = Deck(Nil).createShuffledDeck()

println("Your First Card:")
drawCardsFromDeck(cardDeck, 1)

