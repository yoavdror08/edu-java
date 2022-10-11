package com.shaharyi.maze;

import java.util.Random;

public class Maze {
	public Mat mat;
	private Random rand = new Random();
	private char wall;
	private char crumb;
	private char path;
	private char clear;
	private Point start;
	private Point end;

	public Maze(int w, int h) {
		mat = new Mat(w, h);
		start = new Point(1, 1);
		end = new Point(w - 2, h - 2);
		clear = ' ';
		wall = '#';
		crumb = '.';
		path = '+';
		mat.fill(wall);
		//genRecurse(start);
		genStack(start);
		mat.set(start, 'X');
		mat.set(end, 'X');
	}

	void swap(Object[] arr, int x, int y) {
		Object temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}

	void shuffle(Object[] arr) {
		for (int i = arr.length; i > 0; i--) {
			int r = rand.nextInt(i);
			swap(arr, r, i - 1);
		}
	}

  void shuffleRecurse(Object[] arr, int n) {
    if (n==0)
      return;
    int r = rand.nextInt(n+1);
    swap(arr, n, r);
    shuffleRecurse(arr, n-1);
  }

	public Point[] neighbors(Point p, int step, boolean shuffle) {
		int x = p.x, y = p.y;
		// @formatter:off
		Point[] ret = { 
				new Point(x - step, y), 
				new Point(x + step, y), 
				new Point(x, y - step), 
				new Point(x, y + step) 
		}; 
		// @formatter:on
		if (shuffle)
			shuffleRecurse(ret, ret.length-1);
		return ret;
	}

	public Point middle(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}

	public void genRecurse(Point pos) {
		mat.set(pos, clear);
		Point[] neibors = neighbors(pos, 2, true);
		Point mid;
		for (int i = 0; i < neibors.length; i++) {
			if (mat.get(neibors[i]) == wall) {
        mid = middle(pos, neibors[i]);
				mat.set(mid, clear);
				genRecurse(neibors[i]);
			}
		}
	}

	public void genRecurseRand(Point pos) {
		mat.set(pos, clear);
		Point[] neibors = neighbors(pos, 2, false);
		Point next = null;
		for (int i = neibors.length; i > 0; i--) {
			int r = rand.nextInt(i);
			next = new Point(neibors[r]);
			neibors[r] = neibors[i - 1];
			if (mat.get(next) == wall) {
				mat.set(middle(pos, next), clear);
				genRecurse(next);
			}
		}
	}

	public void genStack(Point pos) {
		Stack<Point> stack = new Stack<>();
		mat.set(pos, clear);
		stack.push(pos);
		Point[] neibors;
    Point mid;
		while (!stack.isEmpty()) {
			pos = stack.pop();
			neibors = neighbors(pos, 2, true);
			for (int i = 0; i < neibors.length; i++) {
				if (mat.get(neibors[i]) == wall) {
					mat.set(neibors[i], clear);
          mid = middle(pos, neibors[i]);
					mat.set(mid, clear);
					stack.push(neibors[i]);
				}
			}
		}
	}

	public void solve() {
		solve(start);
	}

	public boolean solve(Point pos) {
		if (pos.equals(end))
			return true;
		if (mat.get(pos) == wall || mat.get(pos) == crumb)
			return false;
		mat.set(pos, crumb);
		Point[] neibors = neighbors(pos, 1, false);
		for (int i = 0; i < neibors.length; i++) {
			if (solve(neibors[i])) {
				mat.set(pos, path);
				return true;
			}
		}
		return false;
	}

}

