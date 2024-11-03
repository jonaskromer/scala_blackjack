import model.*

import scala.io.StdIn.readLine
import scala.util.Random

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
  override def toString = s"$rank of $suit, Value: ${rank.value}"

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

val d1 = model.Deck(Nil).createShuffledDeck()

// Function to draw a number of cards from the deck
def drawCardsFromDeck(deck: model.Deck, numberOfCards: Int): Unit = {
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
def createDeck(): List[model.Card] =
  for {
    suit <- List(Suit.Spades, Suit.Hearts, Suit.Diamonds, Suit.Clubs)  // Step 1: Iterate over each Suit
    rank <- Rank.values.toList                                          // Step 2: Iterate over each Rank
  } yield model.Card(rank, suit)                                              // Step 3: Yield a Card combining each Rank with each Suit

def shuffleDeck(deck:List[model.Card]): List[model.Card] = Random().shuffle(deck)


val pile = createDeck()
shuffleDeck(pile)


case class Hand(cards: List[model.Card]):
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


val aCard = model.Card(Rank.Two, Suit.Hearts)
val aceCard = model.Card(Rank.Ace, Suit.Spades)
val anotherAce = model.Card(Rank.Ace, Suit.Clubs)
val aSecondCard = model.Card(Rank.King, Suit.Diamonds)
val aNineCard = model.Card(Rank.Nine, Suit.Clubs)

val cardList = List(aCard, aceCard, anotherAce, aSecondCard)
val blackjackList = List(aceCard, aSecondCard)


val playerHand = model.Hand(cardList)
val jackpotHand = model.Hand(blackjackList)

println("This is TUI-Blackjack")

val name = readLine("Enter you Name: ")

println(s"Welcome $name!")

val cardDeck = model.Deck(Nil).createShuffledDeck()

println("Your First Card:")
drawCardsFromDeck(cardDeck, 1)


def draw: Option[(model.Card, model.Deck)] = cards match
  case Nil => None // If deck is empty, return None
  case head :: tail => Some(head, Deck(tail)) // Draw the top card and return it with the rest of the deck

def drawAndDisplay(): model.Deck =
  draw match {
    case Some((card, remainingDeck)) =>
      println(s"Drawn Card: $card")
      println(s"Remaining Cards: ${remainingDeck.cards.size}")
      remainingDeck // Rückgabe des verbleibenden Decks
    case None =>
      println("No cards left!")
      this // Gibt das aktuelle Deck zurück, wenn keine Karten mehr vorhanden sind
  }
