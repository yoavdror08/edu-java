package com.shaharyi.strategy;

import java.util.Scanner;

public class Minimax3d {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int size = 3;
		int color = 1;
		int depth = 3;
		Move move = null;
		Board3d board = new Board3d(size);
		int maxScore = board.getMaxScore();
		System.out.println("Input example: 25 for plate #2, spot #5");
		System.out.println();
		while (board.getWinner() == 0 && !board.isFull()) {
			board.print();
			// System.out.println("current board score: " + board.score());
			if (color == 1) {
				String pos = scanner.next();
				int plate = pos.charAt(0) - '1';
				int s = pos.charAt(1) - 'a';
				move = new Move(new int[] { plate, s / size, s % size });
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

	public static Move negamaxEval(Board3d board, int depth, int alpha, int beta, int color) {
		if (board.isTerminal() || depth == 0)
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
