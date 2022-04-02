package Airbnb;

import java.util.HashMap;
import java.util.*;


public class SlidingPuzzle
{
    public static void main(String[] args)
    {
        SlidingPuzzle sp = new SlidingPuzzle();
        System.out.print(sp.canSolve(new int[][]{{4,1,2},{5,0,3}}));
    }

    String[] dirs = new String[] {"RIGHT" , "DOWN", "LEFT", "UP"};
    public SlidingPuzzle () {}

    public boolean canSolve (int[][] matrix) {
        PuzzleState initState = new PuzzleState(matrix);
        PuzzleState finalState = getFinalState(matrix.length, matrix[0].length);

        Queue<PuzzleState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(initState);
        visited.add (initState.getState());
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i=0; i<size; i++) {
                PuzzleState state = queue.poll();
                if (state.getState().equals(finalState.getState())) {
                    return true;
                }
                for (String dir : dirs) {
                    PuzzleState nextState = state.move(dir);
                    if (nextState == null || visited.contains(nextState.getState())) continue;
                    visited.add (nextState.getState());
                    queue.add (nextState);
                }
            }
        }

        return false;
    }

    public PuzzleState getFinalState (int row, int col) {
        int[][] finalState = new int[row][col];
        for (int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                finalState[i][j] = i*col+j + 1;
            }
        }
        finalState[row - 1][col - 1] = 0;
        return new PuzzleState(finalState);
    }

}



class PuzzleState {
    static Map<String, int[]> dirsMap = new HashMap<>();
    {
        dirsMap.put("UP", new int[]{-1, 0});
        dirsMap.put("DOWN", new int[]{1, 0});
        dirsMap.put("LEFT", new int[]{0, -1});
        dirsMap.put("RIGHT", new int[]{0, 1});
    }
    int[][] board;
    int row;
    int col;
    int[] zeroPoint;
    public PuzzleState (int[][] board) {
        this.board = board;
        this.row = board.length;
        this.col = board[0].length;


        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                if (board[i][j] == 0) zeroPoint = new int[]{i, j};
            }
        }
    }

    public PuzzleState move (String direction) {
        if (dirsMap.get(direction) != null) {
            int[] dir = dirsMap.get(direction);
            int currX = zeroPoint[0];
            int currY = zeroPoint[1];
            int newX = currX + dir[0];
            int newY = currY + dir[1];
            if (newX < 0 || newX >= row || newY < 0 || newY >= col) return null;
            int[][] newBoard = swap (currX, currY, newX, newY);
            return new PuzzleState(newBoard);
        }
        return null;
    }

    private int[][] swap (int x, int y, int newX, int newY) {
        int[][] nextState = new int[row][col];
        for (int i=0; i<row; i++) {
            nextState[i] = board[i].clone();
        }
        int curr = nextState[x][y];
        nextState[x][y] = nextState[newX][newY];
        nextState[newX][newY] = curr;
        return nextState;
    }

    public String getState () {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return getState();
    }
}
