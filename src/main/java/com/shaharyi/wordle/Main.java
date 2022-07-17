package com.shaharyi.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.shaharyi.node.Node;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Node<char[]> bank = new Node<char[]>(null);
		int len = readList("data/wordle_words.txt", bank);
		char[] target = random(bank, len);
		int n = solve(target, bank, len);
		System.out.println(n + " tries");
	}

	static char[] random(Node<char[]> bank, int len) {
		int n = (int) (Math.random() * len);
		Node<char[]> p = bank;
		for (int i = 0; i < n; i++)
			p = p.getNext();
		return p.getValue();
	}

	/*
	 * return number of tries
	 */
	static int solve(char[] target, Node<char[]> bank, int len) {
		char[] e = "#####".toCharArray(); // exact
		char[] a = "#####".toCharArray(); // additional
		char[] b = "#".repeat(26).toCharArray(); // bad letters

		int i = 0;
		boolean done = false;
		while (!done) {
			bank = swapBest(bank, e, a, b);
			char[] g = bank.getValue();
			bank = bank.getNext();
			update(g, target, e, a, b);
			print(new char[][] { g, target, e, a, b });
			done = (count(e) == 5);
			i++;
		}
		return i;
	}

	static int find(char c, char[] a) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == c)
				return i;
		}
		return a.length;
	}

	static int count(char c, char[] a) {
		int r = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == c)
				r++;
		}
		return r;
	}

	static void print(char[][] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(String.valueOf(a[i]) + " ");
		}
		System.out.println();
	}

	static void update(char[] g, char[] t, char[] e, char[] a, char[] b) {
		int j = find('#', a);
		for (int i = 0; i < g.length; i++)
			if (g[i] == t[i])
				if (e[i] != g[i]) {
					e[i] = g[i];
					int f = find(e[i], a);
					if (f < a.length) {
						a[f] = a[--j];
						a[j] = '#';
					}
				}
		for (int i = 0; i < g.length; i++) {
			int c = count(g[i], t);
			if (c == 0)
				b[g[i] - 'a'] = g[i];
			for (int k = 0; k < c - count(g[i], e) - count(g[i], a); k++) {
				a[j++] = g[i];
			}
		}
	}

	static int count(char[] f) {
		int r = 0;
		for (int i = 0; i < f.length; i++) {
			if (f[i] != '#')
				r++;
		}
		return r;
	}

	static Node<char[]> swapBest(Node<char[]> bank, char[] e, char[] a, char[] b) {
		int max = -1;
		Node<char[]> p = new Node<char[]>(null, bank);
		Node<char[]> g = null;
		while (p.hasNext()) {
			int s = score(p.getNext().getValue(), e, a, b);
			if (s > max) {
				max = s;
				g = p;
				if (s == 15) // if it's max score possible, stop search
					break;
			}
			p = p.getNext();
		}
		Node<char[]> r = g.getNext();
		char[] t = r.getValue();
		r.setValue(bank.getValue());
		bank.setValue(t);
		System.out.println(max);
		return bank;
	}

	static int countDistinct(char arr[]) {
		int res = 1;
		for (int i = 1; i < arr.length; i++) {
			int j = 0;
			for (j = 0; j < i; j++)
				if (arr[i] == arr[j])
					break;
			if (i == j)
				res++;
		}
		return res;
	}

	static int score(char[] g, char[] e, char[] a, char[] b) {
		int ce = common(g, e);
		int ca = common(g, a);
		int cb = common(g, b);
		int cu = 5 - countDistinct(g);
		if (count(e) + count(a) < 5)
			return 15 - (ce + ca + cb + cu);
		return ca + 5 - cb;
	}

	static int common(char[] x, char[] y) {
		int r = 0;
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < y.length; j++) {
				if (x[i] == y[j])
					r++;
			}
		}
		return r;
	}

	public static int readList(String filepath, Node<char[]> p) throws FileNotFoundException {
		int i = 1;
		Scanner s = new Scanner(new File(filepath));
		p.setValue(s.nextLine().toCharArray());
		while (s.hasNextLine()) {
			String a = s.nextLine();
			Node<char[]> x = new Node<char[]>(a.toCharArray());
			p.setNext(x);
			p = x;
			i++;
		}
		s.close();
		return i;
	}
}
