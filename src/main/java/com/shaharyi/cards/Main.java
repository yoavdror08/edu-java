package com.shaharyi.cards;

import java.util.Scanner;

import com.shaharyi.node.Node;

public class Main {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		int score1 = 0, score2 = 0;
		while (Math.max(score1, score2) < 5) {
			int tricks = playRound();
			score1 += tricks - 6;
			score2 += 13 - tricks - 6;
			System.out.println("score1: " + score1 + "\n" + "score2: " + score2);
		}
	}

	/**
	 * Hands 0, 2 = first couple Hands 1, 3 = second couple
	 * 
	 * @return number of tricks for first couple (not the dealer)
	 */
	public static int playRound() {
		Deck d = new Deck(true, 0);
		System.out.println(d);
		d.shuffle();
		System.out.println(d);
		Hand[] hands = d.deal(4, 13);
		for (int i = 0; i < hands.length; i++)
			System.out.println(hands[i]);
		Card c = hands[3].top();
		int trumps = c.getSuit();
		System.out.println("Trumps: " + c);
		int tricks = 0;
		for (int t = 0; t < 13; t++) {
			int taker = playTrick(hands, trumps);
			if (taker % 2 == 0)
				tricks++;
		}
		System.out.println("Total tricks: " + tricks);
		return tricks;
	}

	/**
	 * 
	 * @param hands
	 * @param trumps
	 * @return taker index, in 0-3
	 */
	public static int playTrick(Hand[] hands, int trumps) {
		Node<Card> trick = new Node<Card>(null);
		Card best = null;
		int taker = 0;
		for (int i = 0; i < 4; i++) {
			System.out.println("Next to play:\n" + hands[i]);
			for (int j = 0; j < hands[i].getCount(); j++) {
				System.out.print(j + "       ");
			}
			System.out.print("\nPick: ");
			int index = scan.nextInt();
			Card c = hands[i].pop(index);
			if (best == null || better(c, best, trumps)) {
				best = c;
				taker = i;
			}
			trick.setNext(new Node<Card>(c, trick.getNext()));
		}
		System.out.println(trick.getNext());
		System.out.println("\nTrick goes to " + taker);
		return taker;
	}

	public static boolean better(Card a, Card b, int trumps) {
		int sa = a.getSuit();
		int sb = b.getSuit();
		boolean sameSuit = (sa == sb);
		return sameSuit && a.diff(b) > 0 || sa == trumps && sb != trumps;
	}
}
