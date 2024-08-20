package com.shaharyi.maze;

import java.util.Scanner;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Mat {
	private char[][] mat;
	private int width;
	private int height;
	private char fillChar;
	private char borderChar;
	private Scanner scan = new Scanner(System.in);

	public Mat(int width, int height) {
		this.width = width;
		this.height = height;
		mat = new char[height][width];
	}

	public Mat(String[] pic) {
		width = pic[0].length();
		height = pic.length;
		mat = new char[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				mat[i][j] = pic[i].charAt(j);
			}
		}
	}

	public void setFillChar(char c) {
		fillChar = c;
	}

	public void fill(char c) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = c;
			}
		}
	}

	public void frame(char c) {
		for (int j = 0; j < this.width; j++) {
			mat[0][j] = c;
			mat[this.height - 1][j] = c;
		}
		for (int i = 0; i < this.height; i++) {
			mat[i][0] = c;
			mat[i][this.width - 1] = c;
		}
	}

	public void set(int x, int y, char c) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			mat[y][x] = c;
	}

	public char get(int x, int y) {
		if (x >= 0 && x < mat[0].length && y >= 0 && y < mat.length)
			return mat[y][x];
		return '\0';
	}

	public void set(Point p, char c) {
		set(p.x, p.y, c);
	}

	public char get(Point p) {
		return get(p.x, p.y);
	}

	public void print() {
		System.out.println();
		for (int i = 0; i < height; i++) {
//			String b = new String(mat[i]);
			for (int j = 0; j < width; j++) {
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
//			System.out.println(b);
		}
	}

	public int[] findChar(char target) {
		int[] pos = { -1, -1 };
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (mat[i][j] == target) {
					pos[1] = i;
					pos[0] = j;
				}
			}
		}
		return pos;
	}

	public void fill(int x, int y, char borderChar, char fillChar) {
		this.borderChar = borderChar;
		this.fillChar = fillChar;
		floodFill(x, y);
	}

	public void floodFillStack(int x, int y) {
		Deque<int[]> stack = new LinkedList<int[]>();
		stack.push(new int[] { x, y });
		while (!stack.isEmpty()) {
			int[] pos = stack.pop();
			x = pos[0];
			y = pos[1];
			int[][] n = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
			for (int i = 0; i < n.length; i++) {
				x = n[i][0];
				y = n[i][1];
				if (x >= 0 && x < width && y >= 0 && y < height)
					if (mat[y][x] != fillChar && mat[y][x] != borderChar) {
						mat[y][x] = fillChar;
						stack.push(new int[] { x, y });
					}
			}
		}
	}

	public void floodFillQueue(int x, int y) {
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.add(new int[] { x, y });
		while (!queue.isEmpty()) {
			int[] pos = queue.remove();
			x = pos[0];
			y = pos[1];
			int[][] n = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
			for (int i = 0; i < n.length; i++) {
				x = n[i][0];
				y = n[i][1];
				if (x >= 0 && x < width && y >= 0 && y < height)
					if (mat[y][x] != fillChar && mat[y][x] != borderChar) {
						mat[y][x] = fillChar;
						queue.add(new int[] { x, y });
					}
			}
			print();
			scan.nextLine();
		}
	}

	public void floodFill(int x, int y) {
		if (x < 0 || x >= mat[0].length)
			return;
		if (y < 0 || y >= mat.length)
			return;
		if (mat[y][x] != fillChar && mat[y][x] != borderChar) {
			mat[y][x] = fillChar;
			floodFill(x + 1, y);
			floodFill(x, y + 1);
			floodFill(x - 1, y);
			floodFill(x, y - 1);
		}
	}
}
