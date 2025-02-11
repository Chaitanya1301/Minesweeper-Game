package util;

public class MinesweeperUtils {
    private static final char UNREVEALED_CELL = '.';
    private static final char BOMB = '@';
    public static void printGridWithBombs(char[][] grid, boolean[][] revealed, int dimension) {
        System.out.println("   0  1  2  3  4  5  6  7  8");
        for (int i = 0; i < dimension; i++) {
            System.out.print(i+"| ");
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j] == BOMB) {
                    System.out.print(BOMB + "| ");
                } else if (revealed[i][j]) {
                    System.out.print(grid[i][j] + "| ");
                } else {
                    System.out.print(UNREVEALED_CELL + "| ");
                }
            }
            System.out.println();
        }
    }
}
