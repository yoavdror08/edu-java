package com.shaharyi.soduku;

public class MainQ {

	// N is the size of the 2D matrix N*N
	static int N = 9;

	static boolean solveSudoku(int grid[][], int row, int col)
	{
		return true;
	}

	/* A utility function to print grid */
	public static void print(int grid[][]) {
		
	}
	
	// Driver Code
	public static void main(String[] args)
	{
		int grid[][] = { 	{ 3, 0, 6, 5, 0, 8, 4, 0, 0 },
							{ 5, 2, 0, 0, 0, 0, 0, 0, 0 },
							{ 0, 8, 7, 0, 0, 0, 0, 3, 1 },
							{ 0, 0, 3, 0, 1, 0, 0, 8, 0 },
							{ 9, 0, 0, 8, 6, 3, 0, 0, 5 },
							{ 0, 5, 0, 0, 9, 0, 6, 0, 0 },
							{ 1, 3, 0, 0, 0, 0, 2, 5, 0 },
							{ 0, 0, 0, 0, 0, 0, 0, 7, 4 },
							{ 0, 0, 5, 2, 0, 6, 3, 0, 0 } };

		if (solveSudoku(grid, 0, 0))
			print(grid);
		else
			System.out.println("No Solution exists");
	}
	// This is code is contributed by Pradeep Mondal P
}

