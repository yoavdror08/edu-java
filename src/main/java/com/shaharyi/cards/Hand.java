package com.shaharyi.cards;

import com.shaharyi.node.Node;

public class Hand {
	private Node<Card> cards;
	private int count;

	public Hand() {
		cards = null;
		count = 0;
	}

	public int getCount() {
		return count;
	}

	public void insert(Card c) {
		cards = new Node<Card>(c, cards);
		count++;
	}

	public Card findMax(int suit) {
		return find(suit, 1, true);
	}

	public Card findMin(int suit) {
		return find(suit, -1, true);
	}

	public Card findMaxExcept(int suit) {
		return find(suit, 1, false);
	}

	public Card findMinExcept(int suit) {
		return find(suit, -1, false);
	}

	public Card find(int suit, int sign, boolean match) {
		Card r = null;
		Node<Card> p = cards;
		while (p != null) {
			Card c = p.getValue();
			if (match == (c.getSuit() == suit))
				if (r == null || c.diff(r) * sign > 0)
					r = c;
			p = p.getNext();
		}
		return r;
	}

	public Card pop(int index) {
		Node<Card> dummy = new Node<Card>(null, cards);
		Node<Card> p = cards, prev = dummy;
		while (p != null && index > 0) {
			prev = p;
			p = p.getNext();
			index--;
		}
		if (index == 0 && p != null) {
			prev.setNext(p.getNext());
			count--;
			cards = dummy.getNext();
			return p.getValue();
		}
		return null;
	}

	public boolean pop(Card c) {
		Node<Card> prev = new Node<Card>(null, cards);
		Node<Card> p = cards;
		while (p != null) {
			if (p.getValue().equals(c)) {
				prev.setNext(p.getNext());
				count--;
				return true;
			}
			prev = p;
			p = p.getNext();
		}
		return false;
	}

	public Card pop() {
		if (cards == null)
			return null;
		Card c = cards.getValue();
		cards = cards.getNext();
		count--;
		return c;
	}

	public Card top() {
		if (cards == null)
			return null;
		return cards.getValue();
	}

	public String toString() {
		return cards.toString();
	}
}
