package com.shaharyi.sheep;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
	private char[][] matrix;
	private boolean[][] visited;
	private int rows;
	private int cols;

	public Board(int r, int c) {
		matrix = new char[r][c];
		rows = r;
		cols = c;
	}

	public boolean isFenced(int[] t) {
		visited = new boolean[rows][cols];
		return isFenced(t[0], t[1]);
	}

	public boolean isFenced(int r, int c) {
		System.out.println(r + ", " + c);
		if (r < 0 || r > rows || c < 0 || c > cols)
			return false;
		if (visited[r][c])
			return true;
		visited[r][c] = true;    // mark visited
		if (matrix[r][c] == '#')
			return true;
		return isFenced(r - 1, c) && isFenced(r, c + 1) && isFenced(r + 1, c) && isFenced(r, c - 1);
	}

	public int fenced(int[] t) {
		visited = new boolean[rows][cols];
		return fenced(t[0], t[1]);
	}

	public int fenced(int r, int c) {
		if (r < 0 || r > rows || c < 0 || c > cols)
			return -1;
		if (visited[r][c])
			return 0;
		visited[r][c] = true;    // mark visited
		if (matrix[r][c] == '#')
			return 0;
		int s = 0;
		if (matrix[r][c] == 'S')
			s = 1;
		return s + fenced(r - 1, c) + fenced(r, c + 1) + fenced(r + 1, c) + fenced(r, c - 1);
	}

	public Board(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		rows = scanner.nextInt();
		cols = scanner.nextInt();
		matrix = new char[rows][cols];
		int r = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (int c = 0; c < line.length(); c++) {
				matrix[r][c] = line.charAt(c);
			}
			r++;
		}
		scanner.close();
	}

	public int[] find(char x) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == x)
					return new int[] { i, j };
			}
		}
		return null;
	}
	public void print() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
}
