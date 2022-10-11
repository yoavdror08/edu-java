package com.shaharyi.animals;

import java.util.Scanner;

class Stage2 {

	static Scanner scanner = new Scanner(System.in);

	public static void runGame() {
		String fileName = "data/perfect_tree_pre_order.txt";
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
			System.out.println("Is it a " + text + "?");
			String answer = scanner.nextLine();
			if (answer.toUpperCase().startsWith("Y"))
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
		System.out.println("Then what is it?");
		String newThing = scanner.nextLine();
		String oldThing = r.getValue();
		System.out.println("Give me a question that distinguishes " + newThing + " from " + oldThing);
		String question = scanner.nextLine();
		System.out.println("And what is the correct answer for " + newThing + "?");
		String answer = scanner.nextLine();
		BinNode<String> newThingNode = new BinNode<String>(newThing);
		BinNode<String> oldThingNode = new BinNode<String>(oldThing);
		r.setValue(question);
		if (answer.toUpperCase().startsWith("Y")) {
			r.setLeft(newThingNode);
			r.setRight(oldThingNode);
		} else {
			r.setRight(newThingNode);
			r.setLeft(oldThingNode);
		}
		System.out.println("Thanks for teaching me!");
	}
}