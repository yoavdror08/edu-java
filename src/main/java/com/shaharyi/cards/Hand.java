
public class Hand {
	private Node<Card> cards;

	public Hand() {
		cards = null;
	}

	public void insert(Card c) {
		cards = new Node<Card>(c, cards);
	}

	public Card popMax(int suit) {
		return pop(suit, 1, true);
	}

	public Card popMin(int suit) {
		return pop(suit, -1, true);
	}

	public Card popMaxExcept(int suit) {
		return pop(suit, 1, false);
	}

	public Card popMinExcept(int suit) {
		return pop(suit, -1, false);
	}

	public Card pop(int suit, int sign, boolean match) {
		Card r = null;
		Node<Card> p = cards;
		while (p != null) {
			Card c = p.getValue();
			if (match == (c.getSuit() == suit))
				if (r == null || (c.getValue() - r.getValue()) * sign > 0)
					r = c;
			p = p.getNext();
		}
		return r;
	}

	public Card pop() {
		Card c = cards.getValue();
		cards = cards.getNext();
		return c;
	}

	public Card top() {
		return cards.getValue();
	}

	public String toString() {
		return cards.toString();
	}
}
