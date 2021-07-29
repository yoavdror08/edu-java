package com.shaharyi.strategy;

public interface Algorithm {
    public Node search(Board board);    
    public NodeFactory getNodeFactory();	
}
