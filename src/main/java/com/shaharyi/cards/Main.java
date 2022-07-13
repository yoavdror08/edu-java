package com.shaharyi.cards;

import java.util.Scanner;

/**
 * משחק ויסט
 *
 * trick = לקיחה
 */
public class Main {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Lets play Whist!" + "\n(99 to quit)");
		int[] score = new int[2];
		String[] teams = { "Your team", "My team" };
		int round = 0;
		while (Math.max(score[0], score[1]) < 5) {
			round++;
			System.out.println("\nRound No. " + round + " starts.");
			int tricks = playRound();
			System.out.println("Round " + round + " over. Your team total tricks: " + tricks);
			int i = (tricks > 6) ? 0 : 1;
			int points = Math.max(tricks, 13 - tricks) - 6;
			System.out.println(teams[i] + " gets " + points + " points");
			score[i] += points;
			for (int t = 0; t < 2; t++)
				System.out.println(teams[t] + " score: " + score[t]);
		}
		int winner = (score[0] >= 5) ? 0 : 1;
		System.out.println("\nGame over.\n" + teams[winner] + " wins!");
	}

	/**
	 * Hands 0, 2 = first couple Hands 1, 3 = second couple
	 * 
	 * @return number of tricks for first couple (not the dealer)
	 */
	public static int playRound() {
		Deck d = new Deck(true, 0);
//		System.out.println(d);
		d.shuffle();
//		System.out.println(d);
		Hand[] hands = d.deal(4, 13);
		for (int i = 0; i < hands.length; i++)
			System.out.println("#" + i + ": " + hands[i]);
		Card c = hands[3].top();
		int trumps = c.getSuit();
		System.out.println("Trumps indicator: " + c + "\n");
		int tricks = 0;
		int taker = 0;
		for (int t = 1; t <= 13; t++) {
			System.out.println("Trick No. " + t);
			taker = playTrick(hands, trumps, taker);
			if (taker % 2 == 1)
				tricks++;
		}
		return tricks;
	}

	public static Card userPlay(Hand hand) {
		System.out.println("Your turn\n" + hand);
		for (int j = 0; j < hand.getCount(); j++)
			// System.out.print(j + " ".repeat(7));
			System.out.format("%2d%6s", j, "");
		System.out.print("\nPick: ");
		int index = scan.nextInt();
		if (index == 99) {
			System.out.println("My pleasure, goodbye.");
			System.exit(0);
		}
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
			if (best == null || c.betterThan(best, trumps)) {
				best = c;
				taker = player;
			}
			trick[i] = c;
			player = (player + 1) % 4;
		}
		System.out.println("Trick goes to player #" + taker + "\n");
		return taker;
	}
}
