package com.shaharyi.strategy;

public interface Algorithm {
    public Node search(Board board);	
    public Node createNode(Node currentNode, int[] move, int color);	
}
