package com.shaharyi.node;

public class Main {

	public static void main(String[] args) {
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
}
