package com.shaharyi.strategy;

public interface NodeFactory
{
    public Node createNode(Node parent, int[] move, int color);
}
