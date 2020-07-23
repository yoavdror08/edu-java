
public class Node<T> {
	private int[] move;
	private T data;

	private Node parent;
	private Node[] children;

	public Node(Node parent, int[] move, int player) {
		this.data = null;
		this.move = move;
		this.parent = parent;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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
		s += "), data=" + data;
		return s;
	}
}