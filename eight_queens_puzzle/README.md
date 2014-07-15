Eight Queens Puzzle
=============
The idea with this algorithm is to place each queen on the first available square of a chessboard. There are in total eight or more queens depending on the size of the board. The queens cannot interfere with one another. If you put one queen on 1,1 (first row, first column) the algorithm will try to put the next queen on 1,2 (first row, second column) but will see that is not possible and will continue so on so forth. If there is no place for the next queen the algorithm will call a backtrack algorithm which will go back and move the previous queen(s).

## Screenshot
![alt text](https://raw.githubusercontent.com/teodorostlund/java-algorithms/master/eight_queens_puzzle/screenshot.png "Eight Queens Puzzle in terminal")
