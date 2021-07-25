package com.shaharyi.strategy;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

public class Tic extends JPanel implements ActionListener, ItemListener {

	static JFrame frm;
	static Tic tic = null;

	JComboBox depthCombo;
	JComboBox sizeCombo;
	private SingleBoard sboard;
	private char currentPlayer = 'X';

	public static int size = 4;
	public static int depth = 3;

	Board2d board;
	int color = 1;

	// want to store some extra data in our button
	// so we can access it later
	class BoardButton extends JButton {
		public int row, col;

		public BoardButton(int row, int col) {
			this.row = row;
			this.col = col;
			this.setText("   ");
		}

		public String toString() {
			return "(" + row + "," + col + ") = " + this.getText();
		}
	}

	// A panel for rows and cols
	class SingleBoard extends JPanel {
		private BoardButton[] items;

		public SingleBoard(ActionListener listener) {
			items = new BoardButton[size * size];
			setLayout(new GridLayout(size, size));
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					BoardButton b = new BoardButton(row, col);
					b.addActionListener(listener);
					b.setFocusPainted(false);
					add(b);
					items[row * size + col] = b;
				}
			}
		}

		public char getValue(int row, int col) {
			String s = items[row * size + col].getText();
			return (s == null || s.length() == 0) ? ' ' : s.charAt(0);
		}

		public void setValue(int row, int col, char val) {
			items[row * size + col].setText(String.valueOf(val));
			items[row * size + col].setEnabled(false);
		}

	}

	// we can get and set knowing a panel number
	// the let the panel method do the work
	public char getValue(int row, int col, int plane) {
		return sboard.getValue(row, col);
	}

	public void setValue(int row, int col, char val) {
		sboard.setValue(row, col, val);
	}

	public void actionPerformed(ActionEvent evt) {
		BoardButton b = (BoardButton) evt.getSource();
		setValue(b.row, b.col, currentPlayer);
		System.out.println(b);
		Move move = new Move(new int[] { b.row, b.col });
		board.makeMove(move, 1);

		if (!checkGameOver()) {
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			int maxScore = board.getMaxScore();
			move = negamaxEval(board, depth, -maxScore, +maxScore, -1);
			System.out.println(move);
			board.makeMove(move, -1);
			int[] p = move.getPosition();
			setValue(p[0], p[1], currentPlayer);
			checkGameOver();
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
		}

	}

	public boolean checkGameOver() {
		boolean draw = board.isFull();
		if (board.getWinner() != 0 || draw) {
			String msg = draw ? "It's a draw!" : "The winner is " + currentPlayer + "!";
			JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
			restart();
			return true;
		}
		return false;
	}

	public Tic() {
		board = new Board2d(size);
		JPanel settingsPanel = new JPanel(new FlowLayout());
		sizeCombo = new JComboBox<Integer>(new Integer[] { 3, 4, 5, 6 });
		sizeCombo.setSelectedItem(size);
		sizeCombo.addItemListener(this);
		settingsPanel.add(new JLabel("Size: "));
		settingsPanel.add(sizeCombo);
		depthCombo = new JComboBox<Integer>(new Integer[] { 1, 2, 3, 4 });
		depthCombo.setSelectedItem(depth);
		depthCombo.addItemListener(this);
		settingsPanel.add(new JLabel("Level: "));
		settingsPanel.add(depthCombo);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// now we just place the board
		JPanel boardPanel = new JPanel(new GridLayout(1, 1, 10, 10));
		add(settingsPanel);
		add(boardPanel);
		sboard = new SingleBoard(this);
		boardPanel.add(sboard);
	}

	public static void restart() {
		if (tic != null)
			frm.remove(tic);
		tic = new Tic();
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		tic.setBorder(padding);
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add(tic, BorderLayout.CENTER);
		frm.pack();
		frm.setVisible(true);
	}

	public static void main(String[] args) {
		frm = new JFrame("Tic-Tac-Toe");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		restart();
	}

	public static Move negamaxEval(Board2d board, int depth, int alpha, int beta, int color) {
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		// if the state combobox is changed
		if (e.getSource() == depthCombo) {
			depth = (Integer) depthCombo.getSelectedItem();
			System.out.println("Depth is now " + depth);
		}
		if (e.getSource() == sizeCombo) {
			size = (Integer) sizeCombo.getSelectedItem();
			restart();
		}

	}
}
