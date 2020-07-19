import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

public class UltimateTic extends JPanel implements ActionListener, ItemListener {

	static JFrame frm;
	static UltimateTic threeTic = null;

	JComboBox depthCombo;
	JComboBox sizeCombo;
	private SingleBoard[][] boards;
	private char currentPlayer = 'X';

	public static int size = 3;
	public static int depth = 3;

	BoardUltimate board;
	int color = 1;
	Random rand;

	// want to store some extra data in our button
	// so we can access it later
	class BoardButton extends JButton {
		public int row, col, srow, scol;

		public BoardButton(int srow, int scol, int row, int col) {
			this.srow = srow;
			this.scol = scol;
			this.row = row;
			this.col = col;
			this.setText("   ");
		}

		public String toString() {
			return "(" + srow + "," + scol + "," + row + "," + col + ") = " + this.getText();
		}
	}

	// A panel for rows and cols
	class SingleBoard extends JPanel {
		private BoardButton[] items;

		public SingleBoard(int srow, int scol, ActionListener listener) {
			items = new BoardButton[size * size * size];
			setLayout(new GridLayout(size, size));
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					BoardButton b = new BoardButton(srow, scol, row, col);
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
	public char getValue(int srow, int scol, int row, int col) {
		return boards[srow][scol].getValue(row, col);
	}

	public void setValue(int srow, int scol, int row, int col, char val) {
		boards[srow][scol].setValue(row, col, val);
		if (val == 'O')
			if (board.sTicks[srow][scol] < size * size)
				for (int i = 0; i < size; i++)
					for (int j = 0; j < size; j++)
						for (int h = 0; h < size; h++)
							for (int k = 0; k < size; k++) {
								boolean enableBoard = (i == row && j == col);
								BoardButton b = boards[i][j].items[h * size + k];
								b.setEnabled(enableBoard && b.getText().length() != 1);
							}
	}

	Move searchMove(BoardUltimate board, int depth) {
		Move[] moves = board.generateMoves();
		int i = rand.nextInt(moves.length);
		return moves[i];
	}

	public void actionPerformed(ActionEvent evt) {
		BoardButton b = (BoardButton) evt.getSource();
		setValue(b.srow, b.scol, b.row, b.col, currentPlayer);
		System.out.println(b);
		Move move = new Move(new int[] { b.srow, b.scol, b.row, b.col });
		board.makeMove(move, 1);

		if (!checkGameOver()) {
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			int maxScore = board.getMaxScore();
			move = searchMove(board, depth);
			System.out.println(move);
			board.makeMove(move, -1);
			int[] p = move.getPosition();
			setValue(p[0], p[1], p[2], p[3], currentPlayer);
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

	public UltimateTic() {
		rand = new Random();
		board = new BoardUltimate(size);
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
		JPanel boardPanel = new JPanel(new GridLayout(size, size, 10, 10));
		add(settingsPanel);
		add(boardPanel);
		boards = new SingleBoard[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				SingleBoard sb = new SingleBoard(i, j, this);
				boards[i][j] = sb;
				boardPanel.add(sb);
			}
		}
	}

	public static void restart() {
		if (threeTic != null)
			frm.remove(threeTic);
		threeTic = new UltimateTic();
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		threeTic.setBorder(padding);
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add(threeTic, BorderLayout.CENTER);
		frm.pack();
		frm.setVisible(true);
	}

	public static void main(String[] args) {
		frm = new JFrame("Ultimate Tic-Tac-Toe");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		restart();
	}

	public static Move negamaxEval(BoardUltimate board, int depth, int alpha, int beta, int color) {
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
