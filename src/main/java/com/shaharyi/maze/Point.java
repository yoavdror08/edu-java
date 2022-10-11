package com.shaharyi.maze;

public class Point {
	public int x;
	public int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point src) {
		this.x = src.x;
		this.y = src.y;
	}

	public boolean equals(Point p) {
		return (this.x == p.x && this.y == p.y);
	}
}
