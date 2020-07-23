
class MCData {
	private int player;
	private double numWins;
	private int numRollouts;

	public MCData(int player) {
		this.player = player;
		numWins = 0;
		numRollouts = 0;
	}

	public int getPlayer() {
		return player;
	}

	public double getNumWins() {
		return numWins;
	}

	public int getNumRollouts() {
		return numRollouts;
	}

	// result = -1/0/1/2 = p1/draw/p2
	public void update(int result) {
		numRollouts++;
		if (result == player)
			numWins += 1;
		if (result == 0)
			numWins += 0.5;
	}
}