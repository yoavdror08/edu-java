package com.shaharyi.sheep;

import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Board b = new Board("data/sheep1.txt");
		b.print();
		boolean a = b.isFenced(b.find('x'));
		System.out.println(a);
		int s = b.fenced(b.find('x'));
		System.out.println(s);
	}

}
