import java.util.*;

/*
 * Selection:
 *   Traverse the tree to find a leaf node = not "visited" = numRollouts==0
 * Expansion:
 *   Choose or create a child of this leaf with numRollouts=0.
 * Simulation:
 *   Do a complete roll-out while creating nodes on the go, with "numRollouts=0".
 *   For this, use a snapshot of the given board.
 * Back-propagation:
 *   Propagate the result up: 
 *   nominator: +1 if win for this node's player, +0.5 for draw.
 *   denom:     +1 always.
 */
public class MCTS implements Algorithm {

	final static double EPSILON = 1e-6;
	Runtime runtime = Runtime.getRuntime();
	Random rand = new Random();
	long allotedMillis = 10 * 1000;
	long timer;

	// time, memory
	boolean resources_left() {
		return (System.currentTimeMillis() < timer + allotedMillis);
		/*-
		 * long maxMemory = runtime.maxMemory();
		 * long allocatedMemory = runtime.totalMemory();
		 * long freeMemory = runtime.freeMemory();
		 */
	}

	public Node search(Board board) {
		Node<MCData> leaf;
		Node<MCData> current = board.getCurrentNode();
		timer = System.currentTimeMillis();
		while (resources_left()) {
			leaf = traverse(board, current); // select + expand
			int simulation_result = rollout(board, leaf);
			backpropagate(board, leaf, simulation_result);
		}
		return bestChild(current);
	}

	Node<MCData> traverse(Board board, Node<MCData> node) {
		while (fullyExpanded(node)) {
			node = bestUCT(node);
			board.makeMove(node, node.getValue().getPlayer());
		}

		Node<MCData> unvisited = pickUnivisted(node.getChildren());
		// in case no children are present / node is terminal
		return (unvisited != null) ? unvisited : node;
	}

	// Are all children visited?
	boolean fullyExpanded(Node<MCData> node) {
		for (Node<MCData> child : node.getChildren()) {
			if (child.getValue().getNumRollouts() == 0)
				return false;
		}
		return true;
	}

	Node<MCData> bestUCT(Node<MCData> node) {
		Node<MCData> selected = null;
		double bestValue = Double.MIN_VALUE;
		for (Node<MCData> c : node.getChildren()) {
			double totValue = c.getValue().getNumWins();
			int nVisits = c.getValue().getNumRollouts();
			int parentVisits = node.getValue().getNumRollouts();
			double uctValue = totValue / (nVisits + EPSILON)
					+ Math.sqrt(Math.log(parentVisits + 1) / (nVisits + EPSILON)) + rand.nextDouble() * EPSILON;
			// small random number to break ties randomly in unexpanded nodes
			// System.out.println("UCT value = " + uctValue);
			if (uctValue > bestValue) {
				selected = c;
				bestValue = uctValue;
			}
		}
		// System.out.println("Returning: " + selected);
		return selected;
	}

	boolean nonTerminal(Board board) {
		return board.isTerminal();
	}

	// function for the result of the simulation
	int rollout(Board board, Node<MCData> node) {
		while (nonTerminal(board)) {
			node = rolloutPolicy(board, node);
			board.makeMove(node, node.getValue().getPlayer());
		}
		return board.getWinner();
	}

	// function for randomly selecting a child node
	Node rolloutPolicy(Board board, Node<MCData> node) {
		if (node.getChildren() == null)
			board.generateMoves(node.getValue().getPlayer());

		Node[] children = node.getChildren();
		int i = rand.nextInt(children.length);
		return children[i];
	}

	/*
	 * back-propagate without updating the board
	 */
	void backpropagateToRoot(Board board, Node<MCData> node, int result) {
		if (node.getParent() == null)
			return;
		node.getValue().update(result);
		backpropagateToRoot(board, node.getParent(), result);
	}

	void backpropagate(Board board, Node<MCData> node, int result) {
		if (node.getParent() == board.getCurrentNode())
			backpropagateToRoot(board, node, result);
		node.getValue().update(result);
		board.undoMove(node);
		backpropagate(board, node.getParent(), result);
	}

	/*
	 * function for selecting the best child node with highest number of visits
	 */
	Node bestChild(Node node) {
		int max = -1;
		Node best = null;
		for (Node<MCData> child : node.getChildren()) {
			int nRollouts = child.getValue().getNumRollouts();
			if (nRollouts > max) {
				max = nRollouts;
				best = child;
			}
		}
		return best;
	}

	Node<MCData> pickUnivisted(Node[] nodes) {
		for (Node<MCData> node : nodes) {
			if (node.getValue().getNumRollouts() == 0)
				return node;
		}
		return null;
	}
}
