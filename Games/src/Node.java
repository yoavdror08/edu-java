
public class Node<T> {
	private T value;
	private Node<T> parent;
	private Node<T>[] children;
	private int[] move;

	public Node(Node<T> parent, int[] move) {
		this(parent, null, move);
	}

	public Node(Node<T> parent, T value, int[] move) {
		this.move = move;
		this.value = value;
		this.parent = parent;
	}

	public int[] getMove() {
		return move;
	}

	public T getValue() {
		return value;
	}

	public Node<T>[] getChildren() {
		return children;
	}

	public void setChildren(Node<T>[] nodes) {
		this.children = nodes;
	}

	public Node<T> getParent() {
		return parent;
	}

	public String toString() {
		return value.toString();
	}
}