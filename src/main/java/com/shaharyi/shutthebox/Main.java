package com.shaharyi.shutthebox;

/*
 * Shut The Box
 * ============
 * A dice game for 2 players or more.
 * Each player has stones numbered 1 to 9.
 * You have to shut (i.e., discard) all your stones to win.
 * A player rolls the dice and must shut stones that add up to the sum of the dice.
 * If he has no move to play, he is out. His score is the sum of his remaining stones.
 * After every player has taken a turn, the player with the lowest score wins.
 * If a player succeeds in closing all of the numbers, that player is said to have "Shut the Box" – the player wins immediately and the game is over.
 */
public class Main {

	public static void main(String[] args) {
		int numGames = 10000;
		int numPlayers = 4;
		Player[] players = new Player[numPlayers];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i + 1);
		}
		int[] wins = new int[numPlayers];
		for (int i = 0; i < numGames; i++) {
			boolean done = false;
			int out = 0;
			for (int j = 0; j < players.length; j++) {
				players[j].reset();
			}
			int min = 9 * 10 / 2; // sum of 1+2+...+9
			int winIndex = -1;
			while (!done) {
				boolean win = false;
				for (int j = 0; j < players.length && !win; j++) {
					int c1 = (int) (Math.random() * 6) + 1;
					int c2 = (int) (Math.random() * 6) + 1;
					if (!players[j].isOut())
						if (players[j].play(c1, c2)) {
							if (players[j].sumStones() == 0) {
								win = true;
								winIndex = j;
							}
						} else {
							out++;
							if (players[j].sumStones() < min) {
								min = players[j].sumStones();
								winIndex = j;
							}
						}
					done = (out == players.length) || win;
				}
			}
			wins[winIndex]++;
		}
		for (int i = 0; i < wins.length; i++) {
			System.out.println(String.format("strategy = %d: wins[%d] = %2d%%", players[i].getStrategy(), i, wins[i] * 100 / numGames));
		}
	}
}
