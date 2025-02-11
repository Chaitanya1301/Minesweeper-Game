// Main class with the main method
package game;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int dimension = 9;
        int difficulty;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");
        System.out.println("Please choose the difficulty level:");
        System.out.println("0. EASY");
        System.out.println("1. MEDIUM");
        System.out.println("2. HARD");
        
        // Loop until the user enters a valid difficulty level
        do {
            System.out.print("Enter your choice (0/1/2): ");
            difficulty = scanner.nextInt();
            if (difficulty < 0 || difficulty > 2) {
                System.out.println("Invalid input. Please enter 0, 1, or 2.");
            }
        } while (difficulty < 0 || difficulty > 2);

        MinesweeperGame game = new MinesweeperGame(dimension, difficulty);
        game.play();
    }
}
