package com.shaharyi.node;

public class Stack<T>
{
  public Node<T> head;

  public Stack() {
    this.head = null;
  }

  public void push(T x) {
    Node<T> temp = new Node<>(x);
    temp.setNext(head);
    head = temp;
  }

  public T pop() {
    T x = head.getValue();
    head = head.getNext();
    return x;
  }

  public T top() {
    return head.getValue();
  }

  public boolean isEmpty() {
    return head == null;
  }

  @Override
public String toString() {
    if (!isEmpty()) {
      // use Node.toString()
      String temp = head.toString();
      // remove ending "null"
      return "top -> " + temp.substring(0, temp.length() - 4) + " bottom";
    }
    return null;
  }

}
