package com.shaharyi.strategy;

public interface NodeFactory
{
    public Node createNode(Node currentNode, int[] move, int color);
}
