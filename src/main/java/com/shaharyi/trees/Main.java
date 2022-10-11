package com.shaharyi.trees;

import java.util.*;

class Main {

  public static void main(String[] args) {
    BinNode<Integer> t15 = new BinNode<Integer>(15);
    BinNode<Integer> t14 = new BinNode<Integer>(14);
    BinNode<Integer> tree = new BinNode<Integer>(t15, 3, t14);
    BinNode<Integer> t9 = new BinNode<Integer>(9);
    tree.getLeft().setLeft(new BinNode<Integer>(t9, 6, new BinNode<Integer>(7)));
    tree.getLeft().setRight(new BinNode<Integer>(new BinNode<Integer>(34), 56, new BinNode<Integer>(12)));
    System.out.println(tree);

    BinNode<Integer> t = fromString("( ( ( 9 6 7 ) 1 null ) 3 ( null 2 4 ) )");
    
    System.out.println(t);

    // Test your methods here:
    //inOrder(tree);
    // printEven(tree);
    // int count = countNodesAbove(tree, 30);
    // boolean exists = existsValue(tree, 34);
    // boolean each = eachHasTwoChildren(tree);
  }


	/* Construct from in-order with brackets */
	public static BinNode<Integer> fromString(String s) {
		StringTokenizer tokenizer = new StringTokenizer(s);
		return fromString(tokenizer);
	}

  
	/* Construct from in-order with brackets */
	public static BinNode<Integer> fromString(StringTokenizer tokenizer) {
		if (!tokenizer.hasMoreElements())
			return null;
		String s = tokenizer.nextToken();
		if (s.equals("null"))
			return null;
		if (s.equals("(")) {
			BinNode<Integer> left = fromString(tokenizer);
			s = tokenizer.nextToken();
			Integer value = Integer.valueOf(s);
			BinNode<Integer> right = fromString(tokenizer);
			s = tokenizer.nextToken();
			if (!s.equals(")"))
        System.out.println("Note: missing ')'");
			return new BinNode<Integer>(left, value, right);
		}
		return new BinNode<Integer>(Integer.valueOf(s));
	}

  public static void preOrder(BinNode<Integer> t) {
    if (t != null) {
      System.out.println(t.getValue());
      preOrder(t.getLeft());
      preOrder(t.getRight());
    }
  }
  public static <T> void inOrder(BinNode<T> t) {
    if (t != null) {
      inOrder(t.getLeft());
      System.out.println(t.getValue());
      inOrder(t.getRight());
    }
  }

  public static int sumPos(BinNode<Integer> t) {
    if (t == null) 
      return 0;

    int a = 0;
    
    if (t.getValue() > 0)
      a = t.getValue();

    return a + sumPos(t.getLeft()) + sumPos(t.getRight());
  }


  public static int countLeaves(BinNode<Integer> t) {
    if (t == null) 
      return 0;
    if (!t.hasLeft() && !t.hasRight())
        return 1;
    return countLeaves(t.getLeft()) + 
           countLeaves(t.getRight());
  }

  public static void printEven(BinNode<Integer> t) {

    if (t != null) {

      printEven(t.getLeft());

      int value = t.getValue();

      if (value % 2 == 0)
        System.out.println(value);

      printEven(t.getRight());
    }
  }

  public static int countNodesAbove(BinNode<Integer> t, int saf) {
    if (t == null)
      return 0;

    int left = countNodesAbove(t.getLeft(), saf);

    int right = countNodesAbove(t.getRight(), saf);

    int current = 0;
    if (t.getValue() > saf)
      current = 1;

    return current + left + right;
  }

  /*
   * Returns Is there a node with value of x
   */
  public static boolean existsValue(BinNode<Integer> t,int x) {
    if (t == null)
      return false;

    if (t.getValue() == x)
      return true;

    boolean left = existsValue(t.getLeft(), x);

    boolean right = existsValue(t.getRight(), x);

    return left || right;
  }

  public static boolean eachHasTwoChildren(BinNode<Integer> t) {

    // leaf node
    if (!t.hasLeft() && !t.hasRight())
      return true;

    // only one child
    if (!t.hasRight() || !t.hasLeft())
      return false;

    return eachHasTwoChildren(t.getLeft()) && eachHasTwoChildren(t.getRight());
  }


  
}