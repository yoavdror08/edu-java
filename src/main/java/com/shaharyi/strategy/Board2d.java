package com.shaharyi.strategy;

import static java.lang.Math.*;

public class Board2d {
	private int size;
	private int nTicks; // tick counter
	private int[][] m; // matrix
	private int[][][] c; // orthogonal lines
	private int[][] d; // primary diagonals
	private int maxScore; // stands for infinity in board evaluations

	private final int[] COLOR = { -1, 1 };

	public Board2d(int size) {
		nTicks = 0;
		this.size = size;
		m = new int[size][size];
		c = new int[2][2][size];
		d = new int[2][2];
		maxScore = (int) pow(size, 2 * size);
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
		Move[] moves = new Move[size * size - nTicks];
		int n = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (m[i][j] == 0)
					moves[n++] = new Move(new int[] { i, j });
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

	void updateLines(int p, int x, int y, int inc) {
        c[p][0][x] += inc;
        c[p][1][y] += inc;
	}

	void updateDiagonals(int p, int x, int y, int inc) {
		if (x == y)
			d[p][0] += inc;
		if (x == size - 1 - y)
			d[p][1] += inc;
	}

	void updateCounters(int color, int x, int y, int inc) {
		int player = (color == -1) ? 0 : 1;
		updateLines(player, x, y, inc);
		updateDiagonals(player, x, y, inc);
	}

	public void set(int[] pos, int color) {
		int x = pos[0], y = pos[1];
		if (color != 0 && m[x][y] == 0) {
			m[x][y] = color;
			nTicks++;
			updateCounters(color, x, y, +1);
		}
	}

	public void clear(int[] pos) {
		int x = pos[0], y = pos[1];
		int color = m[x][y];
		if (color != 0) {
			m[x][y] = 0;
			nTicks--;
			updateCounters(color, x, y, -1);
		}
	}

	public boolean isFull() {
		return (nTicks == pow(size, 2));
	}

	public void print() {
		char c;
		char[] DISPLAY = { '0', '.', 'X' };
		for (int i = 0; i < size; i++) {
			System.out.print(" ".repeat(2 * i));
			for (int j = 0; j < size; j++) {
				c = DISPLAY[m[i][j] + 1];
				c = (c == '.') ? (char) ('a' + i * size + j) : c;
				System.out.print(c + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public int getWinner() {
		for (int p = 0; p < 2; p++) {
			for (int d = 0; d < 2; d++) {
				for (int i = 0; i < size; i++) {
					if (c[p][d][i] == size)
						return COLOR[p];
				}
			}
			for (int k = 0; k < 2; k++) {
				if (d[p][k] == size)
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
		int size2 = (int) pow(size, 2);
		int s = 0;
		
		//This factor prefers quick wins and slow loses.
		//And it prefers quick blocking even if you lose anyway eventually. 
		double t = (double) nTicks / (nTicks + 1);
		
		for (int p = 0; p < 2; p++) {
			int sign = (p == 0) ? -1 : 1;
			for (int d = 0; d < 2; d++) {
				for (int i = 0; i < size; i++) {
					if (c[1 - p][d][i] == 0)
						s += sign * pow(size2, c[p][d][i] - t);
				}
			}
			for (int k = 0; k < 2; k++) {
				if (d[1 - p][k] == 0)
					s += sign * pow(size2, d[p][k] - t);
			}
		}
		return s;
	}
}
