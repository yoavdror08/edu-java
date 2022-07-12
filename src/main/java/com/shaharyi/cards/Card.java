package com.shaharyi.cards;

/**
 * Deck of cards for the game of Whist.
 *  
 * @author Shahar Y.
 *
 */
public class Card {
	private int value; // in range 2..14 where 11,12,13,14 is for court (AKA faces or honours): J,Q,K,A
	private int suit;  // in range 0..4 where 4 is for Jokers

	private final char SPADES = '\u2660', HEARTS = '\u2665', DIAMONDS = '\u2666', CLUBS = '\u2663', JOKERS = '\u24BF';

	private final char[] SUITS = { SPADES, HEARTS, DIAMONDS, CLUBS, JOKERS };

	private final char[] COURT = { 'J', 'Q', 'K', 'A' };

	public Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
	}

	public boolean equals(Card c) {
		return c.value == value && c.suit == suit;
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
		s = s + value + SUITS[suit];
		return s;
	}

	public String toString() {
		String s = "";
		if (value != 10)
			s = " ";
		if (suit < 5)
			if (value <= 10)
				s = s + value;
			else
				s = s + COURT[value - 11];
		s = s + SUITS[suit];
		return s;
	}
}
