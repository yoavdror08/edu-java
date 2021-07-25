package com.shaharyi.strategy;

import static java.lang.Math.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board2d implements Board, Serializable {
	private int size;
	private int nTicks; // tick counter
	private int[][] m; // matrix
	private int[][][] c; // orthogonal lines
	private int[][] d; // primary diagonals
	private int maxScore; // stands for infinity in board evaluations

	private final int[] COLOR = { -1, 1 };

	private Node currentNode;

    NodeFactory nodeFactory;

    public Board2d(int size, NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
		nTicks = 0;
		this.size = size;
		m = new int[size][size];
		c = new int[2][2][size];
		d = new int[2][2];
		maxScore = (int) pow(size, 2 * size);
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


    public Node getCurrentNode() {
        return currentNode;
    }

    public int get(int[] pos) {
        int x = pos[0], y = pos[1];
        return m[x][y];
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

	public void generateMoves(int color) {	    
        List<Node> moves = new ArrayList<Node>();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (m[i][j] == 0) {
                    Node n = nodeFactory.createNode(
                            currentNode, 
                            new int[] { i, j }, 
                            color);
		            moves.add(n);
				}
		
        Node[] children = (Node[]) moves.toArray(new Node[moves.size()]);
        currentNode.setChildren(children);
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
