package game;

import java.util.Random;
import java.util.Scanner;
import util.MinesweeperUtils;

public class MinesweeperGame {
    private static final int EASY = 0;
    private static final int MEDIUM = 1;
    private static final int HARD = 2;

    private static final char UNREVEALED_CELL = '.';
    private static final char BOMB = '@';

    private static final int[] ROW_OFFSETS = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] COL_OFFSETS = {-1, 0, 1, -1, 1, -1, 0, 1};

    private char[][] grid;
    private boolean[][] revealed;
    private int dimension;
    private int numMines;
    private int numMinesDup;
    private int numRevealed;

    public MinesweeperGame(int dimension, int difficulty) {
        this.dimension = dimension;
        this.numRevealed = 0;
        initializeGrid();
        setMines(difficulty);
    }

    private void initializeGrid() {
        grid = new char[dimension][dimension];
        revealed = new boolean[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = UNREVEALED_CELL;
                revealed[i][j] = false;
            }
        }
    }

    private void setMines(int difficulty) {
        Random random = new Random();
        switch (difficulty) {
            case EASY:
                numMines = dimension;
                numMinesDup = numMines;
                System.out.println(numMines);
                break;
            case MEDIUM:
                numMines = dimension + 1 * 3;
                numMinesDup = numMines;
                break;
            case HARD:
                numMines = dimension + 2 * 3;
                numMinesDup = numMines;
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level.");
        }

        while (numMines > 0) {
            int row = random.nextInt(dimension);
            int col = random.nextInt(dimension);
            if (grid[row][col] != BOMB) {
                grid[row][col] = BOMB;
                numMines--;
            }
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (numRevealed < dimension * dimension - numMines) {
            printGrid();
            int row, col;
            boolean validInput = false;
            do {
                System.out.print("Enter row and column (separated by a space): ");
                row = scanner.nextInt();
                col = scanner.nextInt();
                if (isValidPosition(row, col)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid position. Please enter a valid position.");
                }
            } while (!validInput);

            if (revealCell(row, col)) {
                System.out.println("Game over! You hit a bomb.");
                //printGrid();
                return;
            }
            if (numRevealed == dimension * dimension - numMinesDup) {
                System.out.println("Congratulations! You won!");
                printGrid();
                return;
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < dimension && col >= 0 && col < dimension;
    }

    private boolean revealCell(int row, int col) {
        if (grid[row][col] == BOMB) {
            MinesweeperUtils.printGridWithBombs(grid, revealed, dimension);
            return true;
        }

        if (!revealed[row][col]) {
            revealed[row][col] = true;
            numRevealed++;

            int adjacentBombs = countAdjacentBombs(row, col);
            if (adjacentBombs > 0) {
                grid[row][col] = (char) (adjacentBombs + '0');
            } else {
                grid[row][col] = ' ';
                for (int i = 0; i < 8; i++) {
                    int newRow = row + ROW_OFFSETS[i];
                    int newCol = col + COL_OFFSETS[i];
                    if (isValidCell(newRow, newCol)) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        }

        return false;
    }

    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int newRow = row + ROW_OFFSETS[i];
            int newCol = col + COL_OFFSETS[i];
            if (isValidCell(newRow, newCol) && grid[newRow][newCol] == BOMB) {
                count++;
            }
        }
        return count;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < dimension && col >= 0 && col < dimension;
    }

    private void printGrid() {
        System.out.println("   0  1  2  3  4  5  6  7  8");
        for (int i = 0; i < dimension; i++) {
            System.out.print(i+"| ");
            for (int j = 0; j < dimension; j++) {
                if (revealed[i][j]) {
                    System.out.print(grid[i][j] + "| ");
                } else {
                    System.out.print(UNREVEALED_CELL + "| ");
                }
            }
            System.out.println();
        }
    }
}
