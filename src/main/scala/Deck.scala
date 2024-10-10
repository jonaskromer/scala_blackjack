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
  def draw(): Deck =
    val errmsg = "No cards left!"
    cards match {
      case Nil =>
        println(errmsg)
        this // Returns current Deck if Deck is empty
      case head :: tail =>
        println(s"Drawn Card: $head")
        println(s"Remaining Cards: ${tail.size}")
        Deck(tail) // Returns remaining Deck
    }
