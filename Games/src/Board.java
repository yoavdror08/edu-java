
public interface Board {
	public Board deepClone();

	public int getSize();

	public boolean isTerminal();
	
	public Node getCurrentNode();
	
	public Node createNode(int[] move, int color);
	
	public void makeMove(Node node, int color);

	public void undoMove(Node node);

	public void generateMoves(int color);

	public int get(int[] pos);

	public void set(int[] pos, int color);

	public void clear(int[] pos);

	public boolean isFull();

	public void print();

	// -1/0/1/2, 2 means draw
	public int getWinner();
}
