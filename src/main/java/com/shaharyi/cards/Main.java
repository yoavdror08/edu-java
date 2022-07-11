
public class Main {

	public static void main(String[] args) {
		Node<Card> hand = new Node<Card>(new Card(4, 1));
		Deck d = new Deck(true, 0);
		System.out.println(d);
		d.shuffle();
		System.out.println(d);
		Hand[] hands = d.deal(4, 13);
		for (int i = 0; i < hands.length; i++) {
			System.out.println(hands[i]);
		}
		int score1 = 0, score2 = 0;
		while (Math.max(score1, score2) < 5) {
			int tricks = playRound();
			score1 += tricks - 6;
			score2 += 13 - tricks - 6;
		}
	}

	public static int playRound() {
		Deck d = new Deck(true, 0);
		d.shuffle();
		Hand[] hands = d.deal(4, 13);
		Card c = hands[3].top();
		int trumps = c.getSuit();
		System.out.println("Trumps: " + c);
		
		int tricks = 0;
		return tricks;
	}

}
