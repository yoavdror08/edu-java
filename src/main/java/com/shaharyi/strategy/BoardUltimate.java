package com.shaharyi.strategy;

import static java.lang.Math.*;
import java.util.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BoardUltimate implements Board, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int size;
	int nTicks; // tick counter
	int sTicks[][]; // secondary tick counter
	int[][] pm; // primary matrix
	private int[][][] pc; // primary orthogonal lines
	private int[][] pd; // primary diagonals
	private int[][][][] sm; // secondary matrix
	private int[][][][][] sc; // secondary orthogonal lines
	private int[][][][] sd; // secondary diagonals

	private Node currentNode;

	public Node getCurrentNode() {
		return currentNode;
	}

	private int maxScore; // stands for infinity in board evaluations
    NodeFactory nodeFactory;

	private final int[] COLOR = { -1, 1 };

	public BoardUltimate(int size, NodeFactory nodeFactory) {
	    this.nodeFactory = nodeFactory;
		this.size = size;
		nTicks = 0;
		sTicks = new int[size][size];
		pm = new int[size][size];
		pc = new int[3][2][size]; // 3 = player 1, 2, draw
		pd = new int[3][2]; // 3 = player 1, 2, draw
		sm = new int[size][size][size][size];
		sc = new int[2][size][size][2][size];
		sd = new int[2][size][size][2];
	}

	public Board deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Board) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
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

	public void makeMove(Node node, int color) {
		assert (node.getParent() == currentNode);
		set(node.getMove(), color);
		currentNode = node;
	}

    public Node createNode(int[] move, int color) {
		Node node = nodeFactory.createNode(currentNode, move, color);
		return node;
	}

	public void undoMove(Node node) {
		clear(node.getMove());
		currentNode = node.getParent();
	}

	private void genInnerMoves(int color, int prow, int pcol, List<Node> moves) {
		for (int j = 0; j < size; j++)
			for (int k = 0; k < size; k++)
				if (sm[prow][pcol][j][k] == 0) {
				    Node n = nodeFactory.createNode(
				            currentNode, 
				            new int[] { prow, pcol, j, k }, 
				            color);
					moves.add(n);
				}
	}

	// Our chosen variation of rule:
	// If you were sent to a local board that is terminal,
	// you are free to choose from any legit board left.
	public void generateMoves(int color) {
		int[] pos = currentNode.getMove();
		int prow = pos[2];
		int pcol = pos[3];
		List<Node> moves = new ArrayList<Node>();
		if (pm[prow][pcol] == 0)
			genInnerMoves(color, prow, pcol, moves);
		else
			for (int i = 0; i < size; i++)
				for (int j = 0; j < size; j++)
					if (pm[i][j] == 0)
						genInnerMoves(color, i, j, moves);
		// convert moves to array, into the given array 
		Node[] children = (Node[]) moves.toArray(new Node[moves.size()]);
		currentNode.setChildren(children);
	}

	// returns true if winner
	boolean updateCounters(int x, int y, int[][] c, int[] d, int inc) {
		c[0][x] += inc;
		c[1][y] += inc;
		if (x == y)
			d[0] += inc;
		if (x == size - 1 - y)
			d[1] += inc;
		return max(c[0][x], c[1][y]) == size || max(d[0], d[1]) == size;
	}

	boolean isSecondaryDraw(int x, int y) {
		return getWinner(sc[0][x][y], sd[0][x][y]) == 2 && getWinner(sc[1][x][y], sd[1][x][y]) == 2;
	}

	public boolean isDraw() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < size; j++) {
				int s = 0;
				for (int p = 0; p < 3; p++)
					s += (pc[p][i][j] != 0) ? 1 : 0;
				// if one or more players have an opening
				if (s <= 1)
					return false;
			}
			int s = 0;
			for (int p = 0; p < 3; p++)
				s += (pd[p][i] != 0) ? 1 : 0;
			if (s <= 1)
				return false;
		}
		return true;
	}

	/*
	 * inc = -1/+1 to state if this is doMove or undoMove (clear tick-box)
	 */
	void updateCounters(int color, int x, int y, int z, int w, int inc) {
		int p = (color == -1) ? 0 : 1;

		boolean wasWin = getWinner(sc[p][x][y], sd[p][x][y]) == 1;
		boolean nowWin = updateCounters(z, w, sc[p][x][y], sd[p][x][y], inc);
		if (wasWin != nowWin) {
			pc[p][0][x] += inc;
			pc[p][1][y] += inc;
			pd[p][0] += (x == y) ? inc : 0;
			pd[p][1] += (x == size - 1 - y) ? inc : 0;
			pm[x][y] = nowWin ? color : 0;
		}
		if (isSecondaryDraw(x, y)) {
			pm[x][y] = 2;
			// update draw counters
			pc[2][0][x] += inc;
			pc[2][1][y] += inc;
			pd[2][0] += (x == y) ? inc : 0;
			pd[2][1] += (x == size - 1 - y) ? inc : 0;
		}
	}

	public int get(int x, int y, int z, int w) {
		return sm[x][y][z][w];
	}

	public int get(int[] pos) {
		int x = pos[0], y = pos[1], z = pos[2], w = pos[3];
		return sm[x][y][z][w];
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
		return nTicks == size * size;
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

	// -1/0/1/2, 2 means draw
	public int getWinner() {
		int w, s = 0;
		for (int p = 0; p < 2; p++) {
			w = getWinner(pc[p], pd[p]);
			if (w == 1)
				return COLOR[p];
			s += w;
		}
		if (s == 4 || isDraw())
			return 2;
		return 0;
	}

	/*-
	 * return: 0/1/2
	 * 
	 * 0 = not a winner and some counter is zero, 
	 * 1 = winner, 
	 * 2 = all non-zero
	 */
	public int getWinner(int[][] c, int[] d) {
		boolean noZero = true;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < size; j++) {
				noZero = noZero && c[i][j] > 0;
				if (c[i][j] == size)
					return 1;
			}
			noZero = noZero && d[i] > 0;
			if (d[i] == size)
				return 1;
		}
		return noZero ? 2 : 0;
	}

	/*-
	 * sum of: tsize^(x-1) for each line without opponent. 
	 * (with sign +1 or -1) for
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
