package com.shaharyi.strategy;

public interface Algorithm extends NodeFactory {
	Node search(Board board);	
}
