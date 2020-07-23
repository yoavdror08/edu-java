import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;

public class UltimateTic extends JPanel implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// machine, still on, human, draw
	private final static Color[] TICK_COLOR = { Color.YELLOW, Color.GRAY, Color.GREEN, Color.DARK_GRAY };
	static JFrame frm;
	static UltimateTic threeTic = null;

	JComboBox<Integer> depthCombo;
	JComboBox<Integer> sizeCombo;
	private SingleBoard[][] boards;
	private char currentPlayer = 'X';

	public static int size = 3;
	public static int depth = 3;

	Board board;
	MCTS algorithm;

	int color = 1;
	Random rand;

	class BoardButton extends JButton {
		/**
		 * want to store some extra data in our button so we can access it later
		 */
		private static final long serialVersionUID = 1L;
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
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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

	/*
	 * @TODO: replace the loops: call board.generateMoves() and then for each move
	 * enable the relevant button. This way no need for "uboard".
	 */
	public void setValue(int srow, int scol, int row, int col, char val) {
		BoardUltimate uboard = (BoardUltimate) board;
		boards[srow][scol].setValue(row, col, val);
		boolean destOver = uboard.pm[row][col] != 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				for (int h = 0; h < size; h++)
					for (int k = 0; k < size; k++) {
						BoardButton b = boards[i][j].items[h * size + k];
						if (uboard.pm[i][j] != 0)
							b.setBackground(TICK_COLOR[uboard.pm[i][j] + 1]);
						if (val == 'O') {
							boolean over = (uboard.sTicks[i][j] == size * size || uboard.pm[i][j] != 0);
							boolean destiny = (i == row && j == col);
							boolean enableBoard = !over && (destiny || destOver && !destiny);
							boolean ticked = getValue(i, j, h, k) != ' ';
							b.setEnabled(enableBoard && !ticked);
						}
					}
	}

	Node randomMove(Board board) {
		board.generateMoves(-1);
		Node[] children = board.getCurrentNode().getChildren();
		int i = rand.nextInt(children.length);
		return children[i];
	}

	Node searchMove() {

		// Move move = algorithm.monte_carlo_tree_search(board);
		return randomMove(board);
	}

	public void actionPerformed(ActionEvent evt) {
		BoardButton b = (BoardButton) evt.getSource();
		int[] pos = new int[] { b.srow, b.scol, b.row, b.col };
		Node node = board.createNode(pos, 1);
		board.makeMove(node, 1);
		setValue(b.srow, b.scol, b.row, b.col, currentPlayer);
		System.out.println(b);

		if (!checkGameOver()) {
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
			// int maxScore = board.getMaxScore();
			node = searchMove();
			System.out.println(node);
			board.makeMove(node, -1);
			int[] p = node.getMove();
			setValue(p[0], p[1], p[2], p[3], currentPlayer);
			checkGameOver();
			currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
		}

	}

	public boolean checkGameOver() {
		int status = board.getWinner();
		if (status != 0) {
			String msg = (status == 2) ? "It's a draw!" : "The winner is " + currentPlayer + "!";
			JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
			restart();
			return true;
		}
		return false;
	}

	public UltimateTic() {
		rand = new Random();
		board = new BoardUltimate(size);
		algorithm = new MCTS();
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
		boardPanel.setBackground(Color.ORANGE);
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

	public static Node<NMData> negamaxEval(BoardUltimate board, int depth, int alpha, int beta, int color) {
		if (board.isTerminal() || depth == 0) {
			Node<NMData> node = new Node<NMData>(board.getCurrentNode(), null, color);
			node.getData().setScore(color * board.score());
		}
		board.generateMoves(color);
		Node<NMData>[] moves = board.getCurrentNode().getChildren();
		// board.orderMoves(moves, color);
		Node<NMData> best = null;
		for (Node<NMData> move : moves) {
			if (alpha < beta)
				break;
			board.makeMove(move, color);
			Node<NMData> tmove = negamaxEval(board, depth - 1, -beta, -alpha, -color);
			int score = -tmove.getData().getScore();
			move.getData().setScore(score);
			if (best == null || score > best.getData().getScore())
				best = move;
			board.undoMove(move);
			if (score > alpha) {
				alpha = score;
				best = move;
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
