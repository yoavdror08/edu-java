package com.shaharyi.sheep;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
	private char[][] m;
	private boolean[][] v;
	private int rows;
	private int cols;

	public Board(int r, int c) {
		m = new char[r][c];
		rows = r;
		cols = c;
	}

	public boolean isFenced(int[] t) {
		v = new boolean[rows][cols];
		return isFenced(t[0], t[1]);
	}

	public boolean isFenced(int r, int c) {
		System.out.println(r+","+c);
		if (r < 0 || r > rows || c < 0 || c > cols)
			return false;
		if (v[r][c])
			return true;
		v[r][c] = true;    // mark visited
		if (m[r][c] == '#')
			return true;
		return isFenced(r - 1, c) && isFenced(r, c + 1) && isFenced(r + 1, c) && isFenced(r, c - 1);
	}

	public int fenced(int[] t) {
		v = new boolean[rows][cols];
		return fenced(t[0], t[1]);
	}

	public int fenced(int r, int c) {
		if (r < 0 || r > rows || c < 0 || c > cols)
			return -1;
		if (v[r][c])
			return 0;
		v[r][c] = true;    // mark visited
		if (m[r][c] == '#')
			return 0;
		int s = 0;
		if (m[r][c] == 'S')
			s = 1;
		return s + fenced(r - 1, c) + fenced(r, c + 1) + fenced(r + 1, c) + fenced(r, c - 1);
	}

	public Board(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		rows = scanner.nextInt();
		cols = scanner.nextInt();
		m = new char[rows][cols];
		int r = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (int c = 0; c < line.length(); c++) {
				m[r][c] = line.charAt(c);
			}
			r++;
		}
		scanner.close();
	}

	public int[] find(char x) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				if (m[i][j] == x)
					return new int[] { i, j };
			}
		}
		return null;
	}
	public void print() {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				System.out.print(m[i][j]);
			}
			System.out.println();
		}
	}
}
