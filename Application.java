/*
Name: Emily Ochmann
Course: CEN 3024C
Date: March 9.2026

Class Name: Application

This class controls the overall execution of the VGCM. It manages the main program loop, displays the menu, and
calls the appropriate GameUI methods based on the users input.
 */

import java.util.Scanner;

public class Application {

    private GameUI gameUI;
    private Scanner scanner;

    public Application() {

        GameService gameService = new GameService();
        scanner = new Scanner(System.in);
        gameUI = new GameUI(gameService, scanner);

    }
/* Method Name: start
Purpose: Runs the main program loop. The program repeatedly displays the menu, collects user input, and
performs the requested operation until the user chooses to exit.

Parameters: none
Returns: void
 */
    public void start() {

        boolean running = true;

        while (running) {

            gameUI.displayMenu();

            int choice = getValidatedInt();

            switch (choice) {

                case 1:
                    gameUI.handleAdd();
                    break;

                case 2:
                    gameUI.displayGames();
                    break;

                case 3:
                    gameUI.handleUpdate();
                    break;

                case 4:
                    gameUI.handleDelete();
                    break;

                case 5:
                    gameUI.handleSearch();
                    break;

                case 6:
                    gameUI.handleFileLoad();
                    break;

                case 7:
                    running = false;
                    System.out.println("Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /*
    Method Name: getValidatedInt

    Purpose: Ensure that user enters a valid integer value. If invalid input is entered, the user is prompted
    again until a valid input is provided

    Parameters: none
    Returns: int
     */
    private int getValidatedInt() {

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter a number.");
            scanner.next();
        }

        return scanner.nextInt();
    }
}