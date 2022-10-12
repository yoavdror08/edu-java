# edu-java

## com.shaharyi.floodfill
* Flood-fill area in several ways: recursion, stack, queue, scan-line algorithm
* [Run in replit.com](https://replit.com/@shaharyi/FloodFill)

## com.shaharyi.cards
* Lets you play the game of Whist agains the computer.
* Uses either array or stack for deck.
* Uses linked list for cards in "hand".

## com.shaharyi.sheep
* Finds how many sheep are captured starting a specified spot.
* Uses recursion.
* Sample input board can be found in `data/sheep1.txt`

## com.shaharyi.wordle
* Solves game of Wordle puzzle
* Uses linked list
* Makes use of all possible 5-letter words found in `data/wordle_words.txt`
* [Run in replit.com](https://replit.com/@shaharyi/Wordle)

## com.shaharyi.maze
* Create maze using recursion
* Solve maze using recursion
* [Run in replit.com](https://replit.com/@shaharyi/Maze)

## com.shaharyi.strategy

Play vs. computer opponent a variety of Tic-Tac-Toe variations like 3-D, Ultimate T-T-T.

With algorithms like Minimax, MCTS.

### Build and run 3D Tic-Tac-Toe (uses MiniMax Algorithm)
```
$ cd src
$ ./make_jar_3d
$ java -jar ../bin/3DTTT.jar
```
### Build and run Ultimate Tic-Tac-Toe (uses MCTS Algorithm)
```
$ cd src
$ ./make_jar_ult
$ java -jar ../bin/UltimateTicTacToe.jar
```

### To Do
* In "Traverse()", in case no children are present / node is terminal, returning the node itself accumulates duplicate results.  
We should signal that the search is exhausted (using this UCT).  

* Change to use snapshot of the board before rollout, instead of undoing the moves.

* UltimateBoard does not detect game-over well.  
It also turns boards black for some reason.

* Make Negamax algorithm work with Tic and the rest.

