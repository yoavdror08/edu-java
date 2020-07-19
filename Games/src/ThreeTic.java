import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

public class ThreeTic extends JPanel implements ActionListener, ItemListener {

	static JFrame frm;
	static ThreeTic threeTic = null;

	JComboBox depthCombo;
	JComboBox sizeCombo;
	private SingleBoard[] boards;
	private char currentPlayer = 'X';

	public static int size = 4;
	public static int depth = 3;

	Board3d board;
	int color = 1;

	// want to store some extra data in our button
	// so we can access it later
	class BoardButton extends JButton {
		public int row, col, plane;

		public BoardButton(int row, int col, int plane) {
			this.row = row;
			this.col = col;
			this.plane = plane;
			this.setText("   ");
		}

		public String toString() {
			return "(" + row + "," + col + "," + plane + ") = " + this.getText();
		}
	}

	// A panel for rows and cols
	class SingleBoard extends JPanel {
		private BoardButton[] items;

		public SingleBoard(int plane, ActionListener listener) {
			items = new BoardButton[size * size];
			setLayout(new GridLayout(size, size));
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					BoardButton b = new BoardButton(row, col, plane);
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
		return boards[plane].getValue(row, col);
	}

	public void setValue(int row, int col, int plane, char val) {
		boards[plane].setValue(row, col, val);
	}

	public void actionPerformed(ActionEvent evt) {
		BoardButton b = (BoardButton) evt.getSource();
		setValue(b.row, b.col, b.plane, currentPlayer);
		System.out.println(b);
		Move move = new Move(new int[] { b.plane, b.row, b.col });
		board.makeMove(move, 1);

		if (!checkGameOver()) {
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			int maxScore = board.getMaxScore();
			move = negamaxEval(board, depth, -maxScore, +maxScore, -1);
			System.out.println(move);
			board.makeMove(move, -1);
			int[] p = move.getPosition();
			setValue(p[1], p[2], p[0], currentPlayer);
			checkGameOver();
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
		}

	}

	public boolean checkGameOver() {
		int winner = board.getWinner();
		if (winner != 0 || board.isFull()) {
			String msg = "The winner is " + currentPlayer + "!";
			JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
			restart();
			return true;
		}
		return false;
	}

	public ThreeTic() {
		board = new Board3d(size);
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
		// now we just place the boards
		JPanel boardPanel = new JPanel(new GridLayout(size, 1, 10, 10));
		add(settingsPanel);
		add(boardPanel);
		boards = new SingleBoard[size];
		for (int panel = 0; panel < size; panel++) {
			SingleBoard sb = new SingleBoard(panel, this);
			boards[panel] = sb;
			boardPanel.add(sb);
		}
	}

	public static void restart() {
		if (threeTic != null)
			frm.remove(threeTic);
		threeTic = new ThreeTic();
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		threeTic.setBorder(padding);
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add(threeTic, BorderLayout.CENTER);
		frm.pack();
		frm.setVisible(true);
	}

	public static void main(String[] args) {
		frm = new JFrame("3D Tic-Tac-Toe");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		restart();
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
