
public class Node {
	private int[] move;
	private MCData mcData;
	private NMData nmData;
	
	private Node parent;
	private Node[] children;

	public Node(Node parent, int[] move, int player) {
		this.move = move;
		this.mcData = new MCData(player);
		this.parent = parent;
	}

	public NMData getNmData() {
		return nmData;
	}

	public MCData getMcData() {
		return mcData;
	}

	public int[] getMove() {
		return move;
	}

	public Node[] getChildren() {
		return children;
	}

	public void setChildren(Node[] nodes) {
		this.children = nodes;
	}

	public Node getParent() {
		return parent;
	}

	public String toString() {
		String s = "(" + move[0];
		for (int i = 1; i < move.length; i++) {
			s += "," + move[i];
		}
		s += "), " + mcData.toString();
		return s;
	}
}