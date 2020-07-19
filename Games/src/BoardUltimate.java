import static java.lang.Math.*;

public class BoardUltimate {
	private int size;
	int nTicks; // tick counter
	int sTicks[][]; // secondary tick counter
	private int[][] pm; // primary matrix
	private int[][][][] sm; // secondary matrix
	private int[][][] pc; // primary orthogonal lines
	private int[][] pd; // primary diagonals
	private int[][][][][] sc; // secondary orthogonal lines
	private int[][][][] sd; // secondary diagonals
	
	private Move lastMove;
	
	private int maxScore; // stands for infinity in board evaluations

	private final int[] COLOR = { -1, 1 };

	public BoardUltimate(int size) {
		nTicks = 0;
		this.size = size;
		sTicks = new int[size][size]; 
		pm = new int[size][size];
		pc = new int[2][size][size];
		pd = new int[2][2];
		sm = new int[size][size][size][size];
		sc = new int[2][size][size][size][size];
		sd = new int[2][size][size][2];
	}

	public int getMaxScore() {
		return maxScore;
	}

	public int getSize() {
		return size;
	}

	public boolean isTerminal() {
		return (isFull() || getWinner() != 0);
	}

	public void makeMove(Move move, int color) {
		set(move.getPosition(), color);
		lastMove = move;
	}

	public void undoMove(Move move) {
		clear(move.getPosition());
	}

	public Move[] generateMoves() {
		int n = 0;
		int prow = lastMove.getPosAt(2);
		int pcol = lastMove.getPosAt(3);
		Move[] moves = new Move[size * size - sTicks[prow][pcol]];
		for (int j = 0; j < size; j++)
			for (int k = 0; k < size; k++)
				if (sm[prow][pcol][j][k] == 0)
					moves[n++] = new Move(new int[] { prow, pcol, j, k });
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
			undoMove(moves[i]);
		}
		sortMoves(moves);
	}

	void updateCounters(int color, int x, int y, int z, int w, int inc) {
		int p = (color == -1) ? 0 : 1;
		int before = sc[p][x][y][z][w];
		if (before == size || before + inc == size)
			pc[p][x][y] += inc;
		sc[p][x][y][z][w] += inc;
		if (x == y || x == size - 1 - y) {
			int d = (x == y) ? 0 : 1;
			before = sd[p][x][y][d];
			if (before == size || before + inc == size)
				pd[p][d] += inc;
			sd[p][x][y][d] += inc;
		}
	}

	public void set(int[] pos, int color) {
		int x = pos[0], y = pos[1], z = pos[2], w = pos[3];
		if (color != 0 && sm[x][y][z][w] == 0) {
			sm[x][y][z][w] = color;
			nTicks++;
			sTicks[x][y]++;
			updateCounters(color, x, y, z, w, +1);
		}
	}

	public void clear(int[] pos) {
		int x = pos[0], y = pos[1], z = pos[2], w = pos[3];
		int color = sm[x][y][z][w];
		if (color != 0) {
			sm[x][y][z][w] = 0;
			nTicks--;
			sTicks[x][y]--;
			updateCounters(color, x, y, z, w, -1);
		}
	}

	public boolean isFull() {
		return (nTicks == pow(size, 4));
	}

	public void print() {
		char c;
		char[] DISPLAY = { '0', '.', 'X' };
		for (int k = 0; k < size; k++) {
			for (int h = 0; h < size; h++) {
				for (int i = 0; i < size; i++) {
					System.out.print(" ".repeat(2 * i));
					for (int j = 0; j < size; j++) {
						c = DISPLAY[sm[k][h][i][j] + 1];
						c = (c == '.') ? (char) ('a' + i * size + j) : c;
						System.out.print(c + "  ");
					}
					System.out.println();
				}
				System.out.println();
			}
		}
	}

	public int getWinner() {
		for (int p = 0; p < 2; p++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (pc[p][i][j] == size)
						return COLOR[p];
				}
			}
			for (int i = 0; i < 2; i++) {
				if (pd[p][i] == size)
					return COLOR[p];
			}
		}
		return 0;
	}

	/*
	 * sum of: tsize^(x-1) for each line without opponent. (with sign +1 or -1) for
	 * relevant player
	 */
	public int score() {
		int size3 = (int) pow(size, 3);
		int s = 0;

		// This factor prefers quick wins and slow loses.
		// And it prefers quick blocking even if you lose anyway eventually.
		double t = (double) nTicks / (nTicks + 1);

		for (int p = 0; p < 2; p++) {
			int sign = (p == 0) ? -1 : 1;
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (pc[1 - p][i][j] == 0)
							s += sign * pow(size3, pc[p][i][j] - t);
					}
				}
			}
			for (int i = 0; i < 2; i++) {
				if (pd[1 - p][i] == 0)
					s += sign * pow(size3, pd[p][i] - t);
			}
		}
		return s;
	}
}
