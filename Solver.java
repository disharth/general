
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {

    private SolutionStep solvedState = null;
    private final  MinPQ<SolutionStep> priortyQ = new MinPQ<>(new SolutionStepComparator());
    private final MinPQ<SolutionStep> twinPriortyQ = new MinPQ<>(new SolutionStepComparator());

    private class SolutionStep{
        int movesSoFar;
        Board searchBoard;
        SolutionStep prevStep;

        public SolutionStep(SolutionStep prevBoard ,int movesSoFar , Board searchBoard){
            this.prevStep = prevBoard;
            this.searchBoard = searchBoard;
                this.movesSoFar = movesSoFar;
        }


        public int getMovesSoFar() {
            return movesSoFar;
        }

        public void setMovesSoFar(int movesSoFar) {
            this.movesSoFar = movesSoFar;
        }

        public Board getSearchBoard() {
            return searchBoard;
        }

        public void setSearchBoard(Board searchBoard) {
            this.searchBoard = searchBoard;
        }

        public SolutionStep getPrevStep() {
            return prevStep;
        }

        public void setPrevStep(SolutionStep prevBoard) {
            this.prevStep = prevBoard;
        }
    }

    private class SolutionStepComparator implements Comparator<SolutionStep>{

        @Override
        public int compare(SolutionStep one, SolutionStep other) {
            int priorityOne = one.getMovesSoFar() + one.getSearchBoard().manhattan();
            int priorityOther = other.getMovesSoFar() + other.getSearchBoard().manhattan();
            if (priorityOne > priorityOther) return 1;
            if (priorityOne < priorityOther) return -1;
            return 0;
        }
    }
    private boolean presentInPrev(SolutionStep lastStep,Board board) {
        SolutionStep previousStep = lastStep;
        while (previousStep != null) {
            if (previousStep.getSearchBoard().equals(board)) {
                return true;
            }
            previousStep = previousStep.getPrevStep();
        }
        return false;
    }
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new IllegalArgumentException("Board cannot be null");

        SolutionStep step = new SolutionStep(null,0, initial);
        priortyQ.insert(step);
        if (step.getSearchBoard().isGoal())
            solvedState = step;
        SolutionStep twinStep = new SolutionStep(null,0, initial.twin());
        twinPriortyQ.insert(twinStep);
        while (!step.getSearchBoard().isGoal() && !twinStep.getSearchBoard().isGoal()) {
             step = priortyQ.delMin();
            if (step.getSearchBoard().isGoal())
                solvedState = step;
            for (Board board:step.getSearchBoard().neighbors()){

                if (step.getPrevStep() == null || !presentInPrev(step , board)) {
                    SolutionStep neigbourStep = new SolutionStep(step,step.getMovesSoFar()+1, board);
                    priortyQ.insert(neigbourStep);
                }
            }

            twinStep = twinPriortyQ.delMin();
            for (Board board:twinStep.getSearchBoard().neighbors()){
                if (twinStep.getPrevStep()== null || !presentInPrev(twinStep , board)) {
                    SolutionStep neigbourStep = new SolutionStep(twinStep,twinStep.getMovesSoFar()+1, board);
                    twinPriortyQ.insert(neigbourStep);
                }
            }

        }

    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        if (solvedState != null)
            return true;
        else return false;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!isSolvable()) return -1;
        else return solvedState.movesSoFar;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (solvedState == null) return null;
        Stack<Board> solution = new Stack<>();
        SolutionStep solutionStep = solvedState;
        solution.push(solutionStep.searchBoard);
        while (solutionStep.getPrevStep() != null){
            solutionStep = solutionStep.getPrevStep();
            solution.push(solutionStep.getSearchBoard());
        }
        return solution;

    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        System.out.println("Not Implemeted main.");

    }
}
