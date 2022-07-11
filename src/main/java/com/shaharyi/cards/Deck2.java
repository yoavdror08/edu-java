
public class Deck2 {
	private Card[] cards;
	private int size;

	public Deck2(boolean withJokers) {
		size = (withJokers) ? 54 : 52;
		cards = new Card[size];
		for (int i = 0; i < 52; i++) {
			cards[i] = new Card(i % 13 + 1, i / 13 + 1);
		}
		if (withJokers) {
			cards[52] = new Card(1, 5);
			cards[53] = new Card(1, 5);
		}
	}

	public Card pop() {
		if (size > 0)
			return cards[--size];
		return null;
	}

	public void swap(int i, int j) {
		Card t = cards[i];
		cards[i] = cards[j];
		cards[j] = t;
	}

	public void shuffle() {
		for (int i = 0; i < cards.length; i++) {
			double r = Math.random() * (size - i) + i;
			int k = (int) r;
			swap(i, k);
		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < size; i++) {
			s = s + cards[i];
			if (i % 13 + 1 != 13)
				s += ", ";
			else
				s += "\n";
		}
		return s;
	}

	public Hand[] deal(int numHands, int numCards) {
		Hand[] hands = new Hand[numHands];
		for (int i = 0; i < numHands; i++) {
			hands[i] = new Hand();
		}
		for (int i = 0; i < numCards; i++) {
			for (int j = 0; j < numHands; j++) {
				hands[j].insert(pop());
			}
		}
		return hands;
	}
}
