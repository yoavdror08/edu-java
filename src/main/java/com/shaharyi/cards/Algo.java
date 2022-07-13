package com.shaharyi.cards;

public class Algo {

	public static Card play(Hand hand, Card[] trick, int trumps, int index) {
		Card c = pick(hand, trick, trumps, index);
		hand.pop(c);
		return c;
	}

	public static Card pick(Hand hand, Card[] trick, int trumps, int i) {
		Card c = null;
		if (i == 0) {
			c = hand.findMaxExcept(trumps);
			if (c == null)
				c = hand.findMin(trumps);
			return c;
		}

		Card lead = trick[0];
		int leadSuit = lead.getSuit();
		int leadValue = lead.getValue();
		int highestLead = max(trick, i);
		boolean partnerTrumps = false;
		if (i > 1) // my partner played
			partnerTrumps = (trick[i - 2].getSuit() == trumps);
		switch (i) {
		case 1:
		case 2:
		case 3:
			c = hand.findMax(leadSuit);
			if (c == null || c.getValue() < highestLead || partnerTrumps)
				c = hand.findMin(leadSuit);
			if (c == null)
				c = hand.findMinExcept(leadSuit);
		}

		return c;
	}

	public static int max(Card[] trick, int size) {
		int r = trick[0].getValue();
		for (int i = 1; i < size; i++)
			if (trick[i].getSuit() == trick[0].getSuit())
				r = Math.max(r, trick[i].getValue());
		return r;
	}
}
