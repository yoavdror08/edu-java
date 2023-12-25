package com.shaharyi.node;

public class Main {	
	
	public static void main(String[] args) {
		testIntNode();
		testStack();
	}

	public static void testStack() {
		Stack<Integer> c = new Stack<>();
		c.push(3);
		c.push(5);
		c.push(5);
		c.push(7);
		Stack<Integer> d = new Stack<>();
		d.push(1);
		d.push(3);
		d.push(6);
		d.push(9);
		d.push(10);
		Stack<Integer> r = merge2(c, d);
		System.out.println(r);
	}		
	
	public static Stack<Integer> merge(Stack<Integer> a, Stack<Integer> b) {
		Stack<Integer> c = new Stack<>();

		while (!a.isEmpty() && !b.isEmpty()) {
			if (a.top() >= b.top())
				c.push(a.pop());
			else
				c.push(b.pop());
		}
		while (!a.isEmpty()) {
			c.push(a.pop());
		}
		while (!b.isEmpty()) {
			c.push(b.pop());
		}
		return c;
	}

	public static Stack<Integer> merge2(Stack<Integer> a, Stack<Integer> b) {
		Stack<Integer> c = new Stack<>();

		while (!a.isEmpty() || !b.isEmpty()) {
			if (a.isEmpty())
				c.push(b.pop());
			else if (b.isEmpty())
				c.push(a.pop());
			else if (a.top() > b.top())
				c.push(a.pop());
			else
				c.push(b.pop());
		}
		return c;
	}

	public static IntNode build(int[] a) {
		IntNode p = new IntNode(-1);    // dummy node
		IntNode first = p;		
		IntNode x;
		for (int i = 0; i < a.length; i++) {
			x = new IntNode(a[i]);
			p.setNext(x);
			p = x;
		}
		return first.getNext();    // skip dummy node
	}

	public static boolean q8_contained(IntNode L1, IntNode L2) {
		return false;
	}
	
	public static boolean isIn(IntNode p, int x) {
		if (p == null)
			return false;
		if (p.getValue() == x)
			return true;
		return isIn(p.getNext(), x);
	}
	
	public static void testIntNode() {
		int[] b = { 1, 1, 5, 1, 2, 4, 8 };
		IntNode p = build(b);
		System.out.println(p);
	}
}
