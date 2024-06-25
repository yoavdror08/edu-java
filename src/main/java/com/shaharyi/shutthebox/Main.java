public class Main {

	public static void main(String[] args) {
		int numPlayers = 4;
		Player[] players = new Player[numPlayers];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i + 1);
		}
		int[] wins = new int[numPlayers];
		for (int i = 0; i < 10000; i++) {
			boolean done = false;
			int out = 0;
			int last = 0;
			for (int j = 0; j < players.length; j++) {
				players[j].reset();
			}
			while (!done) {
				for (int j = 0; j < players.length; j++) {
					int c1 = (int) (Math.random() * 6) + 1;
					int c2 = (int) (Math.random() * 6) + 1;
					if (!players[j].isOut())
						if (players[j].play(c1, c2))
							last = j;
						else {
							out++;
							done = (out >= players.length - 1);
						}
				}
			}
			if (!players[last].isOut())
				wins[last]++;
		}
		for (int i = 0; i < wins.length; i++) {
			System.out.println(String.format("wins[%d] = %d", i, wins[i]));
		}
	}
}
