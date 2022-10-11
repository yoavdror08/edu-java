package com.shaharyi.animals;

public class BinNode<T> {
	private BinNode<T> left;
	private T value;
	private BinNode<T> right;

	public BinNode(T value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public BinNode(BinNode<T> left, T value, BinNode<T> right) {
		this.left = left;
		this.value = value;
		this.right = right;
	}

	public T getValue() {
		return value;
	}

	public BinNode<T> getLeft() {
		return left;
	}

	public BinNode<T> getRight() {
		return right;
	}

	public boolean hasLeft() {
		return (left != null);
	}

	public boolean hasRight() {
		return (right != null);
	}

	public void setValue(T value) {
		this.value = value;
	}

	public void setLeft(BinNode<T> left) {
		this.left = left;
	}

	public void setRight(BinNode<T> right) {
		this.right = right;
	}

	public String toString() {
		return " ( " + left + " " + value + " " + right + " ) ";
	}

}
