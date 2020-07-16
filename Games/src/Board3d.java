import static java.lang.Math.*;

public class Board3d {
	private int size;
	private int nTicks;
	private int[][][] m;
	private int[][][][] c;
	private int maxScore;

	private final int[] COLOR = { -1, 1 };

	public Board3d(int size) {
		nTicks = 0;
		this.size = size;
		m = new int[size][size][size];
		c = new int[2][3][size][size];
		maxScore = (int) pow(size, 2 * size);
	}

	public int getMaxScore() {
		return maxScore;
	}

	public int getSize() {
		return size;
	}

	public void makeMove(Move move, int color) {
		set(move.getPosition(), color);
	}

	public void undoMove(Move move) {
		clear(move.getPosition());
	}

	public Move[] generateMoves() {
		Move[] moves = new Move[size * size * size - nTicks];
		int n = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				for (int k = 0; k < size; k++)
					if (m[i][j][k] == 0)
						moves[n++] = new Move(new int[] { i, j, k });
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

	public void set(int[] pos, int color) {
		int x = pos[0], y = pos[1], z = pos[2];
		if (color != 0 && m[x][y][z] == 0) {
			m[x][y][z] = color;
			nTicks++;
			int p = (color == -1) ? 0 : 1;
			c[p][0][x][y]++;
			c[p][1][x][z]++;
			c[p][2][y][z]++;
		}
	}

	public void clear(int[] pos) {
		int x = pos[0], y = pos[1], z = pos[2];
		int color = m[x][y][z];
		if (color != 0) {
			m[x][y][z] = 0;
			nTicks--;
			int p = (color == -1) ? 0 : 1;
			c[p][0][x][y]--;
			c[p][1][x][z]--;
			c[p][2][y][z]--;
		}
	}

	public boolean isFull() {
		return (nTicks == pow(size, 3));
	}

	public void print() {
		char c;
		char[] s = { 'o', '.', 'x' };
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				System.out.print(" ".repeat(2 * i));
				for (int j = 0; j < size; j++) {
					c = s[m[k][i][j] + 1];
					c = (c == '.') ? (char) ('1' + i * size + j) : c;
					System.out.print(c + "  ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public int getWinner() {
		for (int p = 0; p < 2; p++) {
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (c[p][d][i][j] == size)
							return COLOR[p];
					}
				}
			}
		}
		return 0;
	}

	/*
	 * sum of: tsize^(x-1) for each x-in-a-line without opponent. (with sign +1 or
	 * -1) for relevant player
	 */
	public int score() {
		int tsize = (int) pow(size, 3);
		int s = 0;
		for (int p = 0; p < 2; p++) {
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (c[1 - p][d][i][j] == 0) {
							int sign = (p == 0) ? -1 : 1;
							s += sign * pow(tsize, c[p][d][i][j] - 1);
						}
					}
				}
			}
		}
		return s;
	}
}
