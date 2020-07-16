
public class Move {

	private int[] position;
	
	public int[] getPosition() {
		return position;
	}

	private int score;

	public Move(int[] position) {
		this(position, 0);
	}

	public Move(int score) {
		this.score = score;
		this.position = null;
	}

	public Move(int[] position, int score) {
		this.position = new int[position.length];
		for (int i = 0; i < position.length; i++)
			this.position[i] = position[i];
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPosAt(int i) {
		return position[i];
	}
}
