import java.util.Scanner;

class Stage1 {

	static Scanner scanner = new Scanner(System.in);

	public static void runGame() {
		String fileName = "perfect_tree_pre_order.txt";
		BinNode<String> r = TreeUtil.readFromFile(fileName);
		guess(r);
		TreeUtil.writeToFile(r, fileName);
	}

	public static void guess(BinNode<String> r) {
		String text = r.getValue();
		if (text.endsWith("?")) {
			System.out.println(text);
			String answer = scanner.nextLine();
			if (answer.toUpperCase().startsWith("Y"))
				guess(r.getLeft());
			else
				guess(r.getRight());
		} else {
			// it's a leaf node
			System.out.println("Is it a " + text + "? (y/n)");
			char answer = scanner.next().charAt(0);
			if (answer == 'y')
				System.out.println("Yey!");
			else
				newQuestionSubtree(r);
		}
	}
  
	/**
	 * Input a new question from user and build subtree accordingly.
	 * Replace r with the new subtree.
	 * We CANNOT set with "r=" because r is a COPY of reference to the node.
	 * @param r Leaf node with the wrong animal
	 */
	public static void newQuestionSubtree(BinNode<String> r) {
  }
}