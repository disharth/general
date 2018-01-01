

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] boardBlocks;

    private final int manhattan;
    private final int hamming;
    private final int dimension;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        if (blocks == null)
            throw new RuntimeException("Invalid Blocks");
        boardBlocks = new int[blocks.length][blocks[0].length];
        dimension = blocks.length;
        for (int i = 0; i <blocks.length ; i++) {
            for (int j = 0; j <blocks[i].length ; j++) {
                boardBlocks[i][j] = blocks[i][j];
            }
        }
        manhattan = calculateManhattan(blocks);
        hamming = calculateHamming(blocks);

    }

    private int calculateHamming(int[][] blocks){
        int countOfMissplacedItems=0;
        for (int i = 0; i <blocks.length ; i++) {
            for (int j = 0; j <blocks[i].length ; j++) {
                if((i == (blocks.length -1)) && (j == (blocks[i].length -1))) // skip last one for
                    continue;
                int exptectd = (i*blocks[i].length) +(j+1);
                if(blocks[i][j] != exptectd){
                    countOfMissplacedItems++;
                }

            }
        }
        return countOfMissplacedItems;

    }

    private static int calculateManhattan(int[][] blocks){
        int manhattanDis = 0;
        for (int i = 0; i <blocks.length ; i++) {
            for (int j = 0; j <blocks[i].length ; j++) {
                if(blocks[i][j] == 0) // skip last one for
                    continue;
                int iP = blocks[i][j]/(blocks[i].length);
                int jP = ((blocks[i][j]%(blocks[i].length)) - 1);
                if (jP == -1) { // last element of the row.
                    iP = iP - 1;
                    jP = (blocks[i].length) -1;
                }
                manhattanDis += (Math.abs( iP - i) + Math.abs(j -jP));
            }
        }
        return manhattanDis;

    }
    public int dimension()                 // board dimension n
    {
        return dimension;

    }
    public int hamming()                   // number of blocks out of place
    {
        return hamming;

    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;

    }
    public boolean isGoal()                // is this board the goal board?
    {
        for (int i = 0; i <boardBlocks.length ; i++) {
            for (int j = 0; j <boardBlocks[i].length ; j++) {
                if((i == (boardBlocks.length -1)) && (j == (boardBlocks[i].length -1))) // skip last one for
                    continue;
                int exptectd = (i*boardBlocks[i].length) +(j+1);
                if(boardBlocks[i][j] != exptectd){
                    return false;
                }

            }
        }
        return true;

    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] twinBoard = copyArray(boardBlocks);
        int pivotRow =0;
        for (int i = 0; i <twinBoard.length ; i++) {
            for (int j = 0; j <twinBoard[0].length ; j++) {
                if (twinBoard[i][j] == 0){
                    pivotRow = i;
                    break;
                }

            }
        }
        if (pivotRow > 0){
            int tmp = twinBoard[0][0];
            twinBoard[0][0] = twinBoard[0][1];
            twinBoard[0][1] = tmp;
        }else {

            int tmp = twinBoard[1][0];
            twinBoard[1][0] = twinBoard[1][1];
            twinBoard[1][1] = tmp;

        }
        return new Board(twinBoard);

    }
    private int[][] copyArray(int[][] original){
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i <original.length ; i++) {
            for (int j = 0; j <original[i].length ; j++) {
                copy[i][j] = original[i][j];
            }

        }
        return copy;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.boardBlocks.length != that.boardBlocks.length) return false;
        for (int i = 0; i < boardBlocks.length; i++) {
            if (this.boardBlocks[i].length != that.boardBlocks[i].length) return false;
            for (int j = 0; j < boardBlocks[i].length; j++) {
                if (this.boardBlocks[i][j] != that.boardBlocks[i][j]) return false;
            }
        }

        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new BoardIterator();
            }
        };

    }

    private class BoardIterator  implements Iterator<Board>{
        List<Board> neighbours = new ArrayList<>();
        int counter =0;
        public BoardIterator(){

            neighbours = getNeighbours(boardBlocks);
        }
        public List<Board> getNeighbours(int[][] original) {
            int pivotRow=-1 , pivotCol=-1;
            for (int i = 0; i <boardBlocks.length ; i++) {
                for (int j = 0; j <boardBlocks[i].length ; j++) {
                    if(boardBlocks[i][j] ==0){
                        pivotRow = i ;
                        pivotCol = j;
                    }

                }
            }
            Board left = swipeWithLeft(original,pivotRow,pivotCol);
            Board right = swipeWithRight(original,pivotRow,pivotCol);
            Board up = swipeWithUp(original,pivotRow,pivotCol);
            Board below = swipeWithBelow(original,pivotRow,pivotCol);
            if (left != null)
                neighbours.add(left);
            if (right != null)
                neighbours.add(right);
            if (up != null)
                neighbours.add(up);
            if (below != null)
                neighbours.add(below);
            return neighbours;
        }
        private Board swipeWithLeft(int[][] original , int pivotRow , int pivotCol){

               if (pivotCol >0){
                   int[][] aCopy = copyArray(original);
                   aCopy[pivotRow][pivotCol] = aCopy[pivotRow][pivotCol -1];
                   aCopy[pivotRow][pivotCol -1] = 0;
                   return new Board(aCopy);

               }else return null;
        }
        private Board swipeWithRight(int[][] original , int pivotRow , int pivotCol){

            if (pivotCol < original.length -1){
                int[][] aCopy = copyArray(original);
                aCopy[pivotRow][pivotCol] = aCopy[pivotRow][pivotCol +1];
                aCopy[pivotRow][pivotCol +1] = 0;
                return new Board(aCopy);

            }else return null;
        }
        private Board swipeWithUp(int[][] original , int pivotRow , int pivotCol){

            if (pivotRow >0){
                int[][] aCopy = copyArray(original);
                aCopy[pivotRow][pivotCol] = aCopy[pivotRow -1][pivotCol];
                aCopy[pivotRow -1 ][pivotCol] = 0;
                return new Board(aCopy);

            }else return null;
        }
        private Board swipeWithBelow(int[][] original , int pivotRow , int pivotCol){

            if (pivotRow < original[0].length -1){
                int[][] aCopy = copyArray(original);
                aCopy[pivotRow][pivotCol] = aCopy[pivotRow+1][pivotCol];
                aCopy[pivotRow+1][pivotCol] = 0;
                return new Board(aCopy);

            }else return null;
        }
        private int[][] copyArray(int[][] original){
            int[][] copy = new int[original.length][original[0].length];
            for (int i = 0; i <original.length ; i++) {
                for (int j = 0; j <original[i].length ; j++) {
                    copy[i][j] = original[i][j];
                }

            }
            return copy;
        }
        @Override
        public boolean hasNext() {
            return counter < (neighbours.size());
        }

        @Override
        public Board next() {
            if (!hasNext())
                throw new NoSuchElementException("No more element.");
            return neighbours.get(counter++);
        }
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(dimension()).append("\n");
        for (int i = 0; i < boardBlocks.length ; i++) {
            StringBuilder strBuilderLine = new StringBuilder();
            for (int j = 0; j <boardBlocks[i].length ; j++) {
                strBuilderLine.append(" ").append(boardBlocks[i][j]);
            }
            strBuilder.append(strBuilderLine.toString().trim()).append("\n");
        }
        return strBuilder.toString().trim();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        System.out.println("sf");
    }
}
