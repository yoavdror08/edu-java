
public class Deck {
	private Stack<Card> cards;
	private int count;
	private int numJokers;

	public Deck(int numJokers) {
		cards = new Stack<Card>();
		this.numJokers = numJokers;
		this.count = 0;
	}

	public Deck(boolean full, int numJokers) {
		this(numJokers);
		if (full) {
			for (int i = 0; i < 13 * 4; i++) {
				Card c = new Card(i % 13 + 1, i / 13 + 1);
				push(c);
			}
			for (int i = 0; i < numJokers; i++) {
				push(new Card(1, 5));
			}
		}
	}

	public int getCount() {
		return count;
	}

	public void spillOn(Deck d) {
		while (count > 0)
			d.push(pop());
	}

	public void shuffle() {
		Deck d = new Deck(numJokers);
		spillOn(d);
		for (int i = 0; i < d.getCount(); i++) {
			push(d.popRandom());
		}
	}

	public void push(Card c) {
		cards.push(c);
		count++;
	}

	public Card pop() {
		if (cards.isEmpty())
			return null;
		count--;
		return cards.pop();
	}

	public Card popRandom() {
		if (cards.isEmpty())
			return null;
		Stack<Card> backup = new Stack<Card>();
		Card c = null;
		int r = (int) (Math.random() * count + 1);
		for (int i = 0; i < r; i++) {
			backup.push(cards.top());
			c = cards.pop();
		}
		while (!backup.isEmpty())
			cards.push(backup.pop());
		return c;
	}

	public String toString() {
		return cards.toString();
	}

	public String toString1() {
		Stack<Card> backup = new Stack<Card>();
		String s = "";
		for (int i = 0; i < count; i++) {
			backup.push(cards.top());
			s = s + cards.pop();
			if (i % 13 + 1 != 13)
				s += ", ";
			else
				s += "\n";
		}
		while (!backup.isEmpty())
			cards.push(backup.pop());
		return s;
	}

	public Hand[] deal(int numHands, int numCards) {
		Hand[] hands = new Hand[numHands];
		for (int i = 0; i < numHands; i++) {
			hands[i] = new Hand();
		}		
		for (int i = 0; i < numCards; i++) {
			for (int j = 0; j < numHands; j++) {
				if (cards.isEmpty()) {
					System.out.println("Deck got empty while dealing");
					return hands;
				}
				hands[j].insert(pop());
			}
		}
		return hands;
	}
}
