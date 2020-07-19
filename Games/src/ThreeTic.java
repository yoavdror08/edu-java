import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

public class ThreeTic extends JPanel implements ActionListener, ItemListener {
	public static final int SIZE = 4;
	public static int depth = 3;
	
	JComboBox depthCombo;

	Board3d board = new Board3d(SIZE);
	int color = 1;

	public static final int PANELS = SIZE;

	private SingleBoard[] boards = new SingleBoard[PANELS];
	private char currentPlayer = 'X';

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
		public static final int ROWS = SIZE;
		public static final int COLS = SIZE;

		// I hate 2d arrays
		private BoardButton[] items = new BoardButton[ROWS * COLS];

		public SingleBoard(int plane, ActionListener listener) {
			setLayout(new GridLayout(ROWS, COLS));
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					BoardButton b = new BoardButton(row, col, plane);
					b.addActionListener(listener);
					b.setFocusPainted(false);
					add(b);
					items[row * COLS + col] = b;
				}
			}
		}

		// at this level, allow a button value to be changed
		// given a row and col
		// see, you never need 2d arrays
		public char getValue(int row, int col) {
			String s = items[row * COLS + col].getText();
			return (s == null || s.length() == 0) ? ' ' : s.charAt(0);
		}

		public void setValue(int row, int col, char val) {
			items[row * COLS + col].setText(String.valueOf(val));
			items[row * COLS + col].setEnabled(false);
		}

	}

	public ThreeTic() {
		JPanel settingsPanel = new JPanel(new FlowLayout());
		depthCombo = new JComboBox<Integer>(new Integer[] {1,2,3,4});
		depthCombo.setSelectedItem(depth);
		depthCombo.addItemListener(this);
		settingsPanel.add(new JLabel("Level: "));
		settingsPanel.add(depthCombo);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// now we just place the boards		
		JPanel boardPanel = new JPanel(new GridLayout(SIZE, 1, 10, 10));
		add(settingsPanel);
		add(boardPanel);
		for (int panel = 0; panel < PANELS; panel++) {
			SingleBoard sb = new SingleBoard(panel, this);
			boards[panel] = sb;
			boardPanel.add(sb);
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

	static JFrame frm;
	static ThreeTic threeTic = null;

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
            depth = (Integer)depthCombo.getSelectedItem();
            System.out.println("Depth is now " + depth);
        } 
		
	}
}
