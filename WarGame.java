//import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class WarGame {
	public static void main(String[] args) throws IOException {
		WarGame wg = new WarGame();
		Deck d1 = new Deck();
		d1.shuffle();
		Deck d2 = new Deck(d1.split());
		System.out.println("Welcome to War!\nEnter anything to play or stop to quit");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int numRound = 0;
		//	while (!br.readLine().equals("Stop")) {
		while (d1.getDeckSize() != 0 && d2.getDeckSize() != 0) { // use this line for automatic input
			wg.round(d1, d2, new Stack<Card>());
			System.out.println("Player1: " + d1.getDeckSize() + "\nPlayer2: " + d2.getDeckSize());
			numRound += 1;
			System.out.println(numRound);
			if (d1.getDeckSize() == 0) {
				System.out.println("Oh no! You lost!");
			} else if (d2.getDeckSize() == 0) {
				System.out.println("Congratulations! You won!");
			}
		}
		System.out.println("Number of rounds: " + numRound);
		System.out.println("Thanks for playing!");
	}
	public void round(Deck d1, Deck d2, Stack<Card> c) {
		Card player1 = d1.draw();
		Card player2 = d2.draw();
		c.push(player1);
		c.push(player2);
		String output = "You drew " + player1.showCard() + " and your opponent drew " + player2.showCard();
		System.out.println(output);			
		if (player1.getNumber() > player2.getNumber()) {
			addDeck(d1, c);
		} else if (player1.getNumber() == player2.getNumber()) {
			if (d1.getDeckSize() == 1 || d2.getDeckSize() == 1) {
				round(d1, d2, c);
			} else if (d1.getDeckSize() == 0 || d2.getDeckSize() == 0) {
				d1.updateDeck(c); // plan: add all the cards to the bottom of the deck, use for loop, make sure cards in stack is an even #
			} else {
				c.push(d1.draw());
				c.push(d2.draw());
				round(d1, d2, c);
			}



			/*	if (d1.getDeckSize() == 2 || d2.getDeckSize() < 2) {
				round(d1,d2, c);
			} else {
				c.push(d1.draw());
				c.push(d2.draw());
				round(d1, d2, c);
			} */								

			/*if (d1.getDeckSize() == 0 || d2.getDeckSize() == 0) {
				return;
			} else if (d1.getDeckSize() == 1 || d2.getDeckSize() == 1) { // if tie but one player has only 1 card left
				return;		
			} else {
				c.push(d1.draw());
				c.push(d2.draw());
				round(d1, d2, c);
			} */
		} else {
			addDeck(d2, c);	
		}
	}
	public void addDeck(Deck d, Stack<Card> c) { // adding stack to deck
		int size = c.size();
		for(int i = 0; i < size; ++i) {
			d.updateDeck(c.pop());
		}
	}
}

class Deck {
	private Stack<Card> d;
	public Deck() {
		d = new Stack<Card>();
		for (int i = 1; i <= 52; ++i) {
			if (i < 14) {
				d.push(new Card(i, "Diamond"));
			} else if (i < 27) {
				d.push(new Card(i - 13, "Clubs"));
			} else if (i < 40) {
				d.push(new Card(i - 26, "Hearts"));
			} else {
				d.push(new Card(i - 39, "Spades"));
			}
		}
	}
	public Deck(Stack<Card> s) {
		d = s;
	}
	public Stack<Card> split() { // splitting 2 decks
		Stack<Card> temp = new Stack<Card>();
		for(int i = 0; i < 26; ++i) {
			temp.push(d.pop());
		}
		return temp;
	}
	public void shuffle() {
		Stack<Card> temp = new Stack<Card>();
		Random rand = new Random();
		int i = 52;
		while (i > 0) {
			temp.push(d.remove(rand.nextInt(i)));	
			--i;
		}
		d = temp;
	}
	public Card draw() {
		return d.pop();
	}
	public int getDeckSize() {
		return d.size();
	}
	public void updateDeck(Card c) { // adding to bottom of deck
		d.add(0, c);
	}

	public void printDeck () {
		Iterator<Card> it = d.iterator();
		while(it.hasNext())
			System.out.print(it.next().showCard()+", ");
		System.out.println();
	}
}

class Card {
	private int number;
	private String suit;
	public Card(int number, String suit) {
		this.number = number;
		this.suit = suit;
	}
	public String showCard() {
		String cardString;
		switch (number) {
		case 1: 
			cardString = "Ace";
			break;
		case 11:
			cardString = "Jack";
			break;
		case 12:
			cardString = "Queen";
			break;
		case 13:
			cardString = "King";
			break;
		default:
			cardString = Integer.toString(number);
			break;
		}
		return (cardString + " of " + suit);
	}
	public String getSuit() {
		return suit;
	}
	public int getNumber() {
		return number;
	}
}
