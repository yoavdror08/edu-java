import java.util.List;

public interface Board<T> {
	public Board deepClone();

	public int getSize();

	public boolean isTerminal();
	
	public Node<T> getCurrentNode();
	
	public Node<T> createNode(int[] move, int color);
	
	public void makeMove(Node<T> node, int color);

	public void undoMove(Node<T> node);

	public void generateMoves(int color);

	public int get(int[] pos);

	public void set(int[] pos, int color);

	public void clear(int[] pos);

	public boolean isFull();

	public void print();

	// -1/0/1/2, 2 means draw
	public int getWinner();
}
