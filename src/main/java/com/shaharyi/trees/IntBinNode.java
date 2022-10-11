package com.shaharyi.trees;

public class IntBinNode {
  public static void main() {
    IntBinNode t15 = new IntBinNode(15);
    IntBinNode t14 = new IntBinNode(14);
    IntBinNode tree = new IntBinNode(t15, 3, t14);
    IntBinNode t9 = new IntBinNode(9);
    tree.getLeft().setLeft(new IntBinNode(t9, 6, new IntBinNode(7)));
    tree.getLeft().setRight(new IntBinNode(new IntBinNode(34), 56, new IntBinNode(12)));

    System.out.println(tree);
  }
  
  private Integer value;          // Node value
  private IntBinNode right; // next Node
  private IntBinNode left;

  /*
   * Constructor - returns a Node with "value" as value and without sons
   **/
  public IntBinNode(Integer value) {
    this.value = value;
  }

  /*
   * Constructor - returns a Node with "value" as value and its sons
   **/
  public IntBinNode(IntBinNode left, Integer value, IntBinNode right) {
    this.value = value;
    this.right = right;
    this.left = left;
  }

  public IntBinNode getRight() {
    return right;
  }

  public void setRight(IntBinNode right) {
    this.right = right;
  }

  public boolean hasRight() {
    return right != null;
  }

  public IntBinNode getLeft() {
    return left;
  }

  public void setLeft(IntBinNode left) {
    this.left = left;
  }

  public boolean hasLeft() {
    return left != null;
  }

  /* Returns the Node "value" **/
  public Integer getValue() {
    return this.value;
  }

  /* Set the value attribute to be "value" **/
  public void setValue(Integer value) {
    this.value = value;
  }

  /* Returns a string representation of the tree **/
  public String toString() {
    if (left == null && right == null)
      return value.toString();
    return "(" + left + " " + value + " " + right + ")";
  }
}