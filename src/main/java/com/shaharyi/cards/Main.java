package com.shaharyi.cards;

import java.util.Scanner;

import com.shaharyi.node.Node;

public class Main {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		int[] score = new int[2];
		while (Math.max(score[0], score[1]) < 5) {
			int tricks = playRound();
			int i = (tricks > 6) ? 0 : 1;
			score[i] += Math.max(tricks, 13 - tricks) - 6;
			System.out.println("You: " + score[0] + "\n" + "We: " + score[1]);
		}
		String winner = (score[0] >= 5) ? "You" : "We";
		System.out.println(winner + " win!");
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
			System.out.println(i + ") " + hands[i]);
		Card c = hands[3].top();
		int trumps = c.getSuit();
		System.out.println("Trumps: " + c);
		int tricks = 0;
		int taker = 0;
		for (int t = 0; t < 13; t++) {
			taker = playTrick(hands, trumps, taker);
			if (taker % 2 == 0)
				tricks++;
		}
		System.out.println("Your team total tricks: " + tricks);
		return tricks;
	}

	public static Card userPlay(Hand hand) {
		System.out.println("Your turn:\n" + hand);
		for (int j = 0; j < hand.getCount(); j++)
			System.out.print(j + " ".repeat(7));
		System.out.print("\nPick: ");
		int index = scan.nextInt();
		Card c = hand.pop(index);
		return c;
	}

	/**
	 * 
	 * @param hands
	 * @param trumps
	 * @return taker index, in 0-3
	 */
	public static int playTrick(Hand[] hands, int trumps, int player) {
		Card[] trick = new Card[4];
		Card best = null;
		int taker = 0;
		Card c;
		for (int i = 0; i < 4; i++) {
			if (player == 0)
				c = userPlay(hands[player]);
			else
				c = Algo.play(hands[player], trick, trumps, i);
			System.out.println("Player #" + player + " played " + c);
			if (best == null || better(c, best, trumps)) {
				best = c;
				taker = player;
			}
			trick[i] = c;
			player = (player + 1) % 4;
		}
		System.out.println("Trick goes to " + taker + "\n");
		return taker;
	}

	public static boolean better(Card a, Card b, int trumps) {
		int sa = a.getSuit();
		int sb = b.getSuit();
		boolean sameSuit = (sa == sb);
		return sameSuit && a.diff(b) > 0 || sa == trumps && sb != trumps;
	}
}
