import java.util.Scanner;

public class Minimax2 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int row, col, p;
		int size = 3;
		int color = 1;
		int depth = 2;
		Board board = new Board(size);
		int maxScore = board.getMaxScore();
		while (board.getWinner() == 0 && !board.isFull()) {
			board.print();
			//System.out.println("current board score: " + board.score());
			if (color == 1) {
				p = scanner.nextInt() - 1;
				row = p / size;
				col = p % size;
			} else {
				int[] result = negamaxEval(board, depth, -maxScore, +maxScore, color);
				System.out.println("score: " + result[0]);
				row = result[1];
				col = result[2];
			}
			board.set(row, col, color);
			color = -color;
		}
		board.print();
		int winner = board.getWinner();
		if (winner == 0)
			System.out.println("Draw!");
		else
			System.out.println("Winner: " + ((winner == 1) ? "x" : "o"));
		scanner.close();
	}

	/**
	 * full tree minimax: public static int minimax(node) { if computer wins: return
	 * 1 grade = -1 foreach child of node: grade = max( grade, -minimax( child) );
	 * 
	 * return grade }
	 */

	/*
	 * int negamaxEval(node, depth, α, β, color) { if (terminal(node) || depth == 0)
	 * return color * heuristic(node); foreach child of node { alpha := max(α,
	 * -negamaxEval(child, depth-1, -β, -α, -color)); //the following if statement
	 * constitutes alpha-beta pruning if (alpha >= beta) return alpha; } return
	 * alpha; }
	 */
	public static boolean terminal(Board board) {
		return (board.isFull() || board.getWinner() != 0);
	}

	/*
	 * Return int[3] of {score, row, col}
	 */
	public static int[] negamaxEval(Board board, int depth, int alpha, int beta, int color) {
		int row = -1, col = -1;
		if (terminal(board) || depth == 0)
			return new int[] { color * board.score(), row, col };
		int i = 0, j = 0;
		while (i < board.getSize()) {
			if (board.get(i, j) == 0) {
				board.set(i, j, color);
				int val = -negamaxEval(board, depth - 1, -beta, -alpha, -color)[0];
				board.clear(i, j);
				if (val > alpha) {
					alpha = val;
					row = i;
					col = j;
				}
				// the following if statement constitutes alpha-beta pruning
				if (alpha >= beta) {
					return new int[] { alpha, row, col };
				}
			}
			j++;
			if (j == board.getSize()) {
				j = 0;
				i++;
			}
		}
		return new int[] { alpha, row, col };
	}

}
