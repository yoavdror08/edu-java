package com.shaharyi.strategy;

import static java.lang.Math.pow;

public class Negamax implements Algorithm, NodeFactory {
    private int maxScore;
    private int depth;
    
    public Negamax(int size, int dimensions, int depth) {
        maxScore = (int) pow(size, dimensions * size);
        this.depth = depth;
    }
    
    public Node createNode(Node parent, int[] move, int color) {
        Node<NMData> n = new Node<>(parent, move);
        NMData data = new NMData();
        n.setData(data);
        return n;
    }

    public Node search(Board board) {
      Move move = negamaxEval(board, depth, -maxScore, +maxScore, -1);        
      return moveToNode(move);
    }
    
    public Move[] nodesToMoves(Node[] nodes) {
        return null;
    }
    
    public void sortMoves(Move[] moves) {
        for (int i = 0; i < moves.length; i++) {
            for (int j = i + 1; j < moves.length; j++) {
                if (moves[i].getScore() < moves[j].getScore()) {
                    Move t = moves[i];
                    moves[i] = moves[j];
                    moves[j] = t;
                }
            }
        }
    }

    public Node moveToNode(Move m) {
        return new Node(null, m.getPosition());
    }
    public void orderMoves(Board board, Move[] moves, int color) {

        for (int i = 0; i < moves.length; i++) {
            board.makeMove(moveToNode(moves[i]), color);
            moves[i].setScore(color * board.score());
            board.undoMove(moveToNode(moves[i]));
        }
        sortMoves(moves);
    }
    
    public Move negamaxEval(Board board, int depth, int alpha, int beta, int color) {
        if (board.isTerminal() || depth == 0)
            return new Move(color * board.score());
        board.generateMoves(color);
        Node[] nodes = board.getCurrentNode().getChildren();
        Move[] moves = nodesToMoves(nodes); 
        orderMoves(board, moves, color);
        Move best = null;
        for (int i = 0; i < moves.length && alpha < beta; i++) {
            board.makeMove(nodes[i], color);
            Move move = negamaxEval(board, depth - 1, -beta, -alpha, -color);
            int score = -move.getScore();
            moves[i].setScore(score);
            if (best == null || score > best.getScore())
                best = moves[i];
            board.undoMove(nodes[i]);
            if (score > alpha) {
                alpha = score;
                best = moves[i];
            }
        }
        return best;
    }
}