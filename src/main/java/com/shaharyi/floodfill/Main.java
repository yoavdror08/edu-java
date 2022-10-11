package com.shaharyi.floodfill;

public class Main {
	static String[] pic = {
			"....................",
      "..#####....#####....",
      "..#...#....#...###..",
      ".##...######.....##.",
      ".#................#.",
      ".#...........X....#.",
      ".#................#.",
      ".##....#####......#.",
      "..#....#...#......#.",
      "..#....#...#......#.",
      "..######...########.",
      "...................."  
	};
	
	public static void main(String[] args) {
		Mat myMat = new Mat(pic);				
		myMat.print();
		
		int[] start = myMat.findChar('X');
		myMat.floodFill(start[0], start[1], '#', '+');
		myMat.print();		
		System.out.println("Done.");
	}
		
	
}

