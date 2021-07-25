package com.shaharyi.strategy;

import static java.lang.Math.*;

public class Board3d {
	private int size;
	private int nTicks; // tick counter
	private int[][][] m; // 3D-matrix
	private int[][][][] c; // orthogonal lines
	private int[][] pd; // primary diagonals
	private int[][][][] sd; // secondary diagonals
	private int maxScore; // stands for infinity in board evaluations

	private final int[] COLOR = { -1, 1 };

	public Board3d(int size) {
		nTicks = 0;
		this.size = size;
		m = new int[size][size][size];
		c = new int[2][3][size][size];
		sd = new int[2][3][size][2];
		pd = new int[2][4];
		maxScore = (int) pow(size, 3 * size);
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
			moves[i].setScore(color * score());
			undoMove(moves[i]);
		}
		sortMoves(moves);
	}

	void updateLines(int p, int x, int y, int z, int inc) {
		c[p][0][x][y] += inc;
		c[p][1][x][z] += inc;
		c[p][2][y][z] += inc;
	}

	void updateDiagonals(int p, int x, int y, int z, int inc) {
		if (x == y)
			sd[p][2][z][0] += inc;
		if (x == size - 1 - y)
			sd[p][2][z][1] += inc;
		if (x == z)
			sd[p][1][y][0] += inc;
		if (x == size - 1 - z)
			sd[p][1][y][1] += inc;
		if (y == z)
			sd[p][0][x][0] += inc;
		if (y == size - 1 - z)
			sd[p][0][x][1] += inc;
		if (x == y && y == z)
			pd[p][0] += inc;
		if (x == size - 1 - y && y == z)
			pd[p][1] += inc;
		if (y == size - 1 - z && x == z)
			pd[p][2] += inc;
		if (z == size - 1 - x && x == y)
			pd[p][3] += inc;
	}

	void updateCounters(int color, int x, int y, int z, int inc) {
		int player = (color == -1) ? 0 : 1;
		updateLines(player, x, y, z, inc);
		updateDiagonals(player, x, y, z, inc);
	}

	public void set(int[] pos, int color) {
		int x = pos[0], y = pos[1], z = pos[2];
		if (color != 0 && m[x][y][z] == 0) {
			m[x][y][z] = color;
			nTicks++;
			updateCounters(color, x, y, z, +1);
		}
	}

	public void clear(int[] pos) {
		int x = pos[0], y = pos[1], z = pos[2];
		int color = m[x][y][z];
		if (color != 0) {
			m[x][y][z] = 0;
			nTicks--;
			updateCounters(color, x, y, z, -1);
		}
	}

	public boolean isFull() {
		return (nTicks == pow(size, 3));
	}

	public void print() {
		char c;
		char[] DISPLAY = { '0', '.', 'X' };
		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				System.out.print(" ".repeat(2 * i));
				for (int j = 0; j < size; j++) {
					c = DISPLAY[m[k][i][j] + 1];
					c = (c == '.') ? (char) ('a' + i * size + j) : c;
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
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < 2; j++) {
						if (sd[p][d][i][j] == size)
							return COLOR[p];
					}
				}
			}
			for (int k = 0; k < 4; k++) {
				if (pd[p][k] == size)
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
		
		//This factor prefers quick wins and slow loses.
		//And it prefers quick blocking even if you lose anyway eventually. 
		double t = (double) nTicks / (nTicks + 1);
		
		for (int p = 0; p < 2; p++) {
			int sign = (p == 0) ? -1 : 1;
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (c[1 - p][d][i][j] == 0)
							s += sign * pow(size3, c[p][d][i][j] - t);
					}
				}
			}
			for (int d = 0; d < 3; d++) {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < 2; j++) {
						if (sd[1 - p][d][i][j] == 0)
							s += sign * pow(size3, sd[p][d][i][j] - t);
					}
				}
			}
			for (int k = 0; k < 4; k++) {
				if (pd[1 - p][k] == 0)
					s += sign * pow(size3, pd[p][k] - t);
			}
		}
		return s;
	}
}
