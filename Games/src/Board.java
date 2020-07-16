import static java.lang.Math.*;

public class Board {
	private int size;
	private int nTicks;
	private int[][] m;
	private int maxScore;

	public int getMaxScore() {
		return maxScore;
	}

	public int getSize() {
		return size;
	}

	public Board(int size) {
		nTicks = 0;
		this.size = size;
		m = new int[size][size];
		maxScore = (int) pow(size, 2 * size);
	}

	public void makeMove(Move move, int color) {
		int r = move.getPosAt(0), c = move.getPosAt(1);
		set(r, c, color);
	}

	public void undoMove(Move move) {
		int r = move.getPosAt(0), c = move.getPosAt(1);
		clear(r, c);
	}

	public Move[] generateMoves() {
		Move[] moves = new Move[size * size - nTicks];
		int k = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (m[i][j] == 0)
					moves[k++] = new Move(new int[] { i, j });
		return moves;
	}

	public void sortMoves(Move[] moves) {
		for (int i = 0; i < moves.length; i++) {
			for (int j = i + 1; j < moves.length; j++) {
				if (moves[i].getScore() < moves[j].getScore()) {
					Move t = moves[i];
					moves[i] = moves[j];
					moves[j] = t;
				}
			}
		}
	}

	public void orderMoves(Move[] moves, int color) {

		for (int i = 0; i < moves.length; i++) {
			makeMove(moves[i], color);
			moves[i].setScore(score());
			undoMove(moves[i]);
		}
		sortMoves(moves);
	}

	public void set(int row, int col, int color) {
		if (color != 0 && m[row][col] == 0) {
			m[row][col] = color;
			nTicks++;
		}
	}

	public void clear(int row, int col) {
		if (m[row][col] != 0) {
			m[row][col] = 0;
			nTicks--;
		}
	}

	public int get(int row, int col) {
		return m[row][col];
	}

	public boolean isFull() {
		return (nTicks == size * size);
	}

	public void print() {
		char c;
		char[] s = { 'o', '.', 'x' };

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				c = s[m[i][j] + 1];
				c = (c == '.') ? (char) ('1' + i * size + j) : c;
				System.out.print(c + "  ");
			}
			System.out.println();
		}
	}

	public int getWinner() {
		int d1 = 0;
		int d2 = 0;
		for (int i = 0; i < size; i++) {
			int r = 0;
			int c = 0;
			for (int j = 0; j < size; j++) {
				r = r + m[i][j];
				c = c + m[j][i];
			}
			if (abs(r) == size)
				return m[i][0];
			if (abs(c) == size)
				return m[0][i];
			d1 = d1 + m[i][i];
			d2 = d2 + m[i][size - i - 1];
		}
		if (abs(d1) == size)
			return m[0][0];
		if (abs(d2) == size)
			return m[0][size - 1];
		return 0;
	}

	/*
	 * sum of: dsize^(x-1) for each x-in-a-line without opponents with sign +1 or -1
	 * for relevant player
	 */
	public int score() {
		int s = 0;
		int dsize = size * size;
		// Rows and Columns
		for (int i = 0; i < size; i++) {
			int r = m[i][0];
			int c = m[0][i];
			for (int j = 1; j < size; j++) {
				r = (m[i][j] == -signum(r)) ? 0 : r + m[i][j];
				c = (m[j][i] == -signum(c)) ? 0 : c + m[j][i];
			}
			s += signum(r) * pow(dsize, abs(r) - 1);
			s += signum(c) * pow(dsize, abs(c) - 1);
		}
		// Diagonals
		int d1 = m[0][0];
		int d2 = m[0][size - 1];
		for (int i = 1; i < size; i++) {
			d1 = (m[i][i] == -m[0][0]) ? 0 : d1 + m[i][i];
			d2 = (m[i][size - 1 - i] == -m[0][size - 1]) ? 0 : d2 + m[i][size - 1 - i];
		}
		s += signum(d1) * pow(dsize, abs(d1) - 1);
		s += signum(d2) * pow(dsize, abs(d2) - 1);
		return s;
	}
}
