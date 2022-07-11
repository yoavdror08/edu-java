
public class Card {
	private int value;
	private int suit;

	private char[] COURT = { 'J', 'Q', 'K' };

	private final char SPADES = '\u2660', HEARTS = '\u2665', DIAMONDS = '\u2666', CLUBS = '\u2663', JOKERS = '\u24BF';
	private final char[] SUITS = { SPADES, HEARTS, DIAMONDS, CLUBS, JOKERS };

	public Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public int getSuit() {
		return suit;
	}

	public String toStringNum() {
		String s = "";
		if (value < 10)
			s += " ";
		s = s + value + SUITS[suit - 1];
		return s;
	}

	public String toString() {
		String s = "";
		if (value != 10)
			s = " ";
		if (suit < 5)
			if (value == 1)
				s = s + 'A';
			else if (value <= 10)
				s = s + value;
			else
				s = s + COURT[value - 11];
		s = s + SUITS[suit - 1];
		return s;
	}
}
