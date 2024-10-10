import scala.io.StdIn.readLine


@main def hello(): Unit =
  println("This is TUI-Blackjack")

  val name = readLine("Enter you Name: \n--> ")
  println(s"Welcome $name!")

  var cardDeck = Deck(Nil).createShuffledDeck()

  var input = readLine("To draw a card press `d`, to stop the game press `q`\n--> ")

  while(input != "q")

    if (input == "d")
      cardDeck = cardDeck.draw()
    
    input = readLine("Continue by pressing `d` or quit by pressing `q`\n--> ")


  println(s"Goodbye $name")

