import java.util.Scanner;

public class Minimax {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int size = 3;
		int color = 1;
		int depth = 2;
		Move move = null;
		TicBoard board = new TicBoard(size);
		int maxScore = board.getMaxScore();
		while (board.getWinner() == 0 && !board.isFull()) {
			board.print();
			// System.out.println("current board score: " + board.score());
			if (color == 1) {
				int p = scanner.nextInt() - 1;
				move = new Move(new int[] { p / size, p % size });
			} else {
				move = negamaxEval(board, depth, -maxScore, +maxScore, color);
				System.out.println("score: " + move.getScore());
			}
			board.makeMove(move, color);
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
	public static boolean terminal(TicBoard board) {
		return (board.isFull() || board.getWinner() != 0);
	}

	public static Move negamaxEval(TicBoard board, int depth, int alpha, int beta, int color) {
		if (terminal(board) || depth == 0)
			return new Move(color * board.score());
		Move[] moves = board.generateMoves();
		board.orderMoves(moves, color);
		Move best = null;
		for (int i = 0; i < moves.length && alpha < beta; i++) {
			board.makeMove(moves[i], color);
			Move move = negamaxEval(board, depth - 1, -beta, -alpha, -color);
			int score = -move.getScore();
			moves[i].setScore(score);
			if (best == null || score > best.getScore())
				best = moves[i];
			board.undoMove(moves[i]);
			if (score > alpha) {
				alpha = score;
				best = moves[i];
			}
		}
		return best;
	}

}
