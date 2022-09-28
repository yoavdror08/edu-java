# edu-java

Play vs. computer opponent a variety of Tic-Tac-Toe variations like 3-D, Ultimate T-T-T.

With algorithms like Minimax, MCTS.

## Build and run 3D Tic-Tac-Toe (uses MiniMax Algorithm)
```
$ cd src
$ ./make_jar_3d
$ java -jar ../bin/3DTTT.jar
```
## Build and run Ultimate Tic-Tac-Toe (uses MCTS Algorithm)
```
$ cd src
$ ./make_jar_ult
$ java -jar ../bin/UltimateTicTacToe.jar
```

## To Do
* In "Traverse()", in case no children are present / node is terminal, returning the node itself accumulates duplicate results.  
We should signal that the search is exhausted (using this UCT).  

* Change to use snapshot of the board before rollout, instead of undoing the moves.

* UltimateBoard does not detect game-over well.  
It also turns boards black for some reason.

* Make Negamax algorithm work with Tic and the rest.
